package com.xinshijie.gallery;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.service.IReptileImageService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.xinshijie.gallery.vo.ReptilePage;
import com.xinshijie.gallery.vo.ReptileRule;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = GalleryApplication.class)
public class ReptileMain {
    @Autowired
    private IReptileImageService reptileService;

    @Test
    public  void detail(){

        //链式构建请求
        String result = HttpRequest.get( "https://admin.aiavr.com/wiki/reptileRule/getInfo/11")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getIntValue("code") != 200) {
            throw new ServiceException(" reptileRuleVo 出现异常");
        }
        ReptileRule reptileRule = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);
        Document doc = reptileService.requestUrl("https://xiutaku.com/14310", reptileRule, 0);
        Element body = doc.body();
        Element cont = body.select(reptileRule.getStoryPageRule()).first();
        Elements storyList = cont.select(reptileRule.getStoryPageGroupRule());
        if (storyList != null) {
            for (Element story : storyList) {
                Element hrefElement = story.select(reptileRule.getStoryPageHrefRule()).first();
                if (hrefElement != null) {
                    String detailUrl = hrefElement.attr("href");
                    String imgUrl = "";
                    if (StringUtils.isNotEmpty(reptileRule.getStoryPageImgRule())) {
                        Element imgUrlEle = story.select(reptileRule.getStoryPageImgRule()).first();
                        if (imgUrlEle != null) {
                            imgUrl = imgUrlEle.attr("src");
                        }
                    }
                    reptileService.detail(detailUrl, imgUrl, reptileRule);
                }
            }
        }
    }

    @Test
    public  void test10(){
        reptileService.ayacData(10);
    }





}
