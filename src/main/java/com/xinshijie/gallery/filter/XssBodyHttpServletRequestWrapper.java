package com.xinshijie.gallery.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.util.JsoupUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author 卢煌
 * @version 1.0
 * @Description: 定义请求包装类
 * @date 2021-07-26 17:37
 */
@Slf4j
public class XssBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public final String body;

    /**
     * @param request
     */
    public XssBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream in = super.getInputStream();
        StringBuffer bodyStr = new StringBuffer();
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader buffer = new BufferedReader(reader);
        String line = buffer.readLine();
        while (line != null) {
            bodyStr.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        reader.close();
        in.close();
        body = bodyStr.toString();
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (!StrUtil.hasEmpty(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (!StrUtil.hasEmpty(value)) {
                    value = JsoupUtil.clean(value);
                }
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameters = super.getParameterMap();
        Map<String, String[]> map = new LinkedHashMap<>();
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                String[] values = parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if (!StrUtil.hasEmpty(value)) {
                        value = JsoupUtil.clean(value);
                    }
                    values[i] = value;
                }
                map.put(key, values);
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (!StrUtil.hasEmpty(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String values;
        try {
            JSONObject map = JSONObject.parseObject(body);
            Map<String, Object> resultMap = new HashMap(map.size());
            for (String key : map.keySet()) {
                Object val = map.get(key);
                if (map.get(key) instanceof String) {
                    resultMap.put(key, JsoupUtil.clean(val.toString()));
                } else {
                    resultMap.put(key, analysisSubset(val));
                }
            }
            values = JSONUtil.toJsonStr(resultMap);
        } catch (ClassCastException ex) {
            log.info("xss getInputStream 转成出现异常:地址 {},参数：{}", getRequestURI(), body);
            values = body;
        } catch (JSONException e) {
            log.info("xss getInputStream 转成出现异常:地址 {},参数：{}", getRequestURI(), body);
            values = body;
        } catch (NullPointerException e) {
            log.info("xss getInputStream 转成出现空异常:地址 {},参数：{}", getRequestURI(), body);
            values = body;
        }
        final ByteArrayInputStream bain = new ByteArrayInputStream(values.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bain.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }

    /**
     * 解析子集 ,多层对象嵌套与对象数组。
     */
    public static Object analysisSubset(Object val) {
        if (val instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) val;
            Map<String, Object> resultMap = new HashMap(map.size());
            for (String key : map.keySet()) {
                Object subset = map.get(key);
                Object subsetValue = analysisSubset(subset);
                resultMap.put(key, subsetValue);
            }
            return resultMap;
        }
        if (val instanceof JSONArray) {
            List<Object> resultList = new ArrayList<>(((JSONArray) val).size());
            for (int i = 0; i < ((JSONArray) val).size(); i++) {
                Object subsetValue = analysisSubset(((JSONArray) val).get(i));
                resultList.add(subsetValue);
            }
            return resultList;
        }
        if (val instanceof String) {
            return JsoupUtil.clean(val.toString());
        } else {
            return val;
        }
    }
}