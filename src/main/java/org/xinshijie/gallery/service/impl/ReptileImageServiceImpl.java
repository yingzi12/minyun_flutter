package org.xinshijie.gallery.service.impl;


import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xinshijie.gallery.common.ServiceException;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.service.IReptileImageService;
import org.xinshijie.gallery.service.ImageService;
import org.xinshijie.gallery.vo.ReptilePage;
import org.xinshijie.gallery.vo.ReptileRule;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class ReptileImageServiceImpl implements IReptileImageService {

     @Autowired
     private AlbumService albumService;

     @Autowired
    private ImageService imageService;

     @Value("${reptile.url}")
     private String reptileUrl;

     @Async
     public void ayacData(Integer id){
         //链式构建请求
         String result = HttpRequest.get(reptileUrl+"/wiki/reptileRule/getInfo/"+id)
                 .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                 .timeout(20000)//超时，毫秒
                 .execute().body();
         JSONObject jsonObject=JSONObject.parseObject(result);
         if(jsonObject.getIntValue("code")!=200){
             throw new ServiceException(" reptileRuleVo 出现异常");
         }
         ReptileRule reptileRuleVo = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);

         //链式构建请求
         String result2 = HttpRequest.get(reptileUrl+"/wiki/reptilePage/getList/"+id)
                 .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                 .timeout(20000)//超时，毫秒
                 .execute().body();

         JSONObject pageJson=JSONObject.parseObject(result2);
         if(jsonObject.getIntValue("code")!=200){
             throw new ServiceException("pageJson 出现异常");
         }
         List<ReptilePage> pageList = JSON.parseArray(pageJson.getJSONArray("data").toJSONString(), ReptilePage.class);
         orderBySingle(reptileRuleVo,pageList);
     }

     @Async
    public void singleData(){
        //链式构建请求
        String ruleJson = HttpRequest.get(reptileUrl+"/wiki/reptileRule/getList/4")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject ruleListObject=JSONObject.parseObject(ruleJson);
        if(ruleListObject.getIntValue("code")!=200){
            throw new ServiceException(" ruleList 出现异常");
        }
        List<ReptileRule> ruleList = JSON.parseArray(ruleListObject.getJSONArray("data").toJSONString(), ReptileRule.class);

        for(ReptileRule reptileRule:ruleList) {
            //链式构建请求
//            String result = HttpRequest.get(reptileUrl + "/wiki/reptileRule/getInfo/" + id)
////                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
////                 .form(paramMap)//表单内容
//                    .timeout(20000)//超时，毫秒
//                    .execute().body();
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            if (jsonObject.getIntValue("code") != 200) {
//                throw new ServiceException(" reptileRuleVo 出现异常");
//            }
//            ReptileRule reptileRuleVo = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);

            //链式构建请求
            String result2 = HttpRequest.get(reptileUrl + "/wiki/reptilePage/getList/" + reptileRule.getId())
                    .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                    .timeout(20000)//超时，毫秒
                    .execute().body();

            JSONObject pageJson = JSONObject.parseObject(result2);
            if (pageJson.getIntValue("code") != 200) {
                throw new ServiceException("pageJson 出现异常");
            }
            List<ReptilePage> pageList = JSON.parseArray(pageJson.getJSONArray("data").toJSONString(), ReptilePage.class);
            orderBySingle(reptileRule, pageList);


        }
    }

    public void orderBySingle(ReptileRule reptileRule, List<ReptilePage> pageList ){
        if(reptileRule==null){
            throw new ServiceException("错误");
        }
        for(ReptilePage pageVo:pageList) {
            Integer pageStart=pageVo.getNowPage();
            if(reptileRule.getIsHead()==1){
                pageStart=1;
            }
            for(int i=pageStart;i<= pageVo.getPageTotal();i++) {
                String url = pageVo.getPageUrl();
                if(pageVo.getPageSize()>0){
                    url = pageVo.getPageUrl().replaceAll("<地址>",((i-1)*20)+"" );
                }else {
                    url = pageVo.getPageUrl().replaceAll("<地址>",i+"" );
                }
                try {

                    Document doc = requestUrl(url,reptileRule,0);
                    Element body = doc.body();
                    Element cont = body.select(reptileRule.getStoryPageRule()).first();
                    Elements storyList = cont.select(reptileRule.getStoryPageGroupRule());
                    if (storyList != null) {
                        for (Element story : storyList) {
                            Element hrefElement = story.select(reptileRule.getStoryPageHrefRule()).first();
                            if(hrefElement !=null) {
                                String detailUrl = hrefElement.attr("href");
                                String imgUrl="";
                                if(StringUtils.isNotEmpty(reptileRule.getStoryPageImgRule())){
                                    Element imgUrlEle = story.select(reptileRule.getStoryPageImgRule()).first();
                                    if(imgUrlEle!=null){
                                        imgUrl=imgUrlEle.attr("src");
                                    }
                                }
                                detail(detailUrl,imgUrl, reptileRule);
                            }else{
                                addError(1,story.html(),reptileRule.getId(),url);
                            }
                        }
                    }
                    if(i%10==0){
                        System.out.println("---------------++++++++++++++++++++++--------------------------");
                    }
                    reptileRule.setStatus(2);
                } catch (Exception e) {
                    addError(1,e.getMessage(),reptileRule.getId(),url);
                    log.error("出现异常 方法:{} reId:{} message","orderByExecutor",reptileRule.getId(),e);
                }
                pageVo.setNowPage(i);
                updatePage(pageVo);
            }
        }
        reptileRule.setEndTime(LocalDateTime.now());
//        reptileRuleService.updateById(reptileRule);
    }
    public void detail(String url,String imgUrl, ReptileRule reptileRule ){
        String detailUrl=url;
        try {
            if(StringUtils.isNotEmpty(reptileRule.getStoryUrl())){
                detailUrl=reptileRule.getStoryUrl()+url;
            }else {
                if (reptileRule.getStoryUrlType() != null) {
                    detailUrl = reptileRule.getStoryUrl() + url;
                }
            }
            Document doc = requestUrl(detailUrl,reptileRule,0);

            String title = extractContent(doc, reptileRule.getTitleRule());

            String gril = extractContent(doc, reptileRule.getAuthorRule());
            String desc = extractContent(doc, reptileRule.getDescRule());

            Long hash = generate12DigitHash(detailUrl, title);
            Album album= albumService.getInfoBytitle(title);

            if(album==null){
                album=new Album();
                album.setSourceWeb(reptileRule.getStoryUrl());
                album.setSourceUrl(detailUrl);
                album.setUrl(detailUrl);
                album.setImgUrl(imgUrl);
                album.setCountError(0);
                album.setCountSee(0L);
                album.setCreateTime(LocalDate.now().toString());
                album.setGril(gril);
                album.setIntro(desc);
                album.setHash(hash);
                album.setTitle(title);
                albumService.add(album);
                album= albumService.getInfoBytitle(title);
            }else{
                //判断是否是同一组
                if(hash.equals(album.getHash())||StringUtils.isEmpty(album.getGril())||StringUtils.isEmpty(album.getUrl())||StringUtils.isEmpty(album.getIntro())){
                    album.setHash(hash);
                    album.setSourceUrl(detailUrl);
                    if(StringUtils.isNotEmpty(desc)) {
                        album.setIntro(desc);
                    }
                    if(StringUtils.isNotEmpty(imgUrl)) {
                        album.setImgUrl(imgUrl);
                    }
                    if(StringUtils.isNotEmpty(detailUrl)) {
                        album.setUrl(detailUrl);
                    }
                    if(StringUtils.isNotEmpty(gril)) {
                        album.setGril(gril);
                    }
                    albumService.updateById(album);
                }
                //判断是否需要强制更新
                if(reptileRule.getIsUpdate()==2){
                    //判断图片地址是否有效,无效会全部删除
                    boolean isImage=isCheckImage(album.getId(),reptileRule);
                    if(isImage){
                        return;
                    }
                }else {
                    //更新内容
                    album=new Album();
                    album.setSourceWeb(reptileRule.getStoryUrl());
                    album.setSourceUrl(detailUrl);
                    album.setImgUrl(imgUrl);
                    album.setCreateTime(LocalDate.now().toString());
                    album.setGril(gril);
                    album.setHash(hash);
                    album.setId(album.getId());
                    albumService.updateById(album);
                    //删除记录
                    imageService.delAlum(album.getId());

                    album= albumService.getInfoBytitle(title);
                }
            }


            if(StringUtils.isEmpty(reptileRule.getContentPageRule())) {
                addImageList(detailUrl,album,reptileRule);
            }else{
                Element body = doc.body();
                Element imagePageCoent = body.select(reptileRule.getContentPageRule()).first();
                if (imagePageCoent != null) {
                    Elements imageListElement = imagePageCoent.select(reptileRule.getChapterPageRule());
                    for (Element element : imageListElement) {
                        String pageUrl = element.select(reptileRule.getChapterPageUrlRule()).first().attr("href");
                        if(StringUtils.isNotEmpty(reptileRule.getPageUrl())){
                            pageUrl=reptileRule.getPageUrl()+pageUrl;
                        }
                        addImageList(pageUrl,album,reptileRule);
                    }
                }
            }

        } catch (Exception e) {
            addError(1,e.getMessage(),reptileRule.getId(),detailUrl);

            log.error("线程名-地址：{}，  href:{}",Thread.currentThread().getName(),detailUrl,e);
        }
    }

    public void addImageList(String detailUrl,Album album,ReptileRule reptileRule){
        Document doc = requestUrl(detailUrl,reptileRule,0);
        if(doc == null){
            return;
        }
        Element body = doc.body();

        Element chapterListContent = body.select(reptileRule.getChapterListRule()).first();
        if (chapterListContent != null) {
            Elements imageListElement = chapterListContent.select(reptileRule.getChapterGroupRule());
            List<Image> iamgeBatchInsertList = new CopyOnWriteArrayList<>();
            for (Element element : imageListElement) {
                String imageUrlSource="";
                Element imgElemnt = element.select(reptileRule.getChapterUrlRule()).first();
                if("a".equals(reptileRule.getChapterUrlRule())){
                     imageUrlSource =imgElemnt .attr("href");
                }
                if("img".equals(reptileRule.getChapterUrlRule())){
                     imageUrlSource = imgElemnt.attr("src");
                }
                if (StringUtils.isNotEmpty(imageUrlSource)){
                    Image image=new Image();
                    image.setAid(album.getId());
                    try {
                        URL url = new URL(imageUrlSource);
                        String domain = url.getProtocol() + "://" + url.getHost();
                        String path = url.getPath() + (url.getQuery() != null ? "?" + url.getQuery() : "");
                        image.setSourceWeb(domain);
                        image.setUrl(path);
                    } catch (MalformedURLException e) {
                        image.setSourceWeb("");
                        image.setUrl(imageUrlSource);
                        throw new RuntimeException(e);
                    }
                    iamgeBatchInsertList.add(image);
                }
            }
            imageService.addBatch(iamgeBatchInsertList);
            iamgeBatchInsertList.clear();
        }
    }
    public Document requestUrl(String url,ReptileRule reptileRule,Integer replyCount){
         if(replyCount>3){
             return null;
         }
        Connection connection= Jsoup.connect(url)
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .timeout(5000);
        try {
            if(StringUtils.isNotEmpty(reptileRule.getHost())){
                connection.header("Host",reptileRule.getHost());
            }else{
                URL urlPath = new URL(url);
                // 使用getHost()方法获取主机部分
                String host = urlPath.getHost();
                connection.header("Host",host);
            }

            return connection.get();
        } catch (Exception e) {
            addError(1,e.getMessage(),reptileRule.getId(),url);
            log.error("线程名-地址：{}， url：{}",Thread.currentThread().getName(),url,e);
            requestUrl(url,reptileRule,replyCount+1);
        }
        return null;
    }

    public  boolean isURLValid(String urlString,ReptileRule reptileRule) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            if(StringUtils.isNotEmpty(reptileRule.getHost())){
                connection.setRequestProperty("Host",reptileRule.getHost());
            }else{
//                URL urlPath = new URL(url);
                // 使用getHost()方法获取主机部分
                String host = url.getHost();
                connection.setRequestProperty("Host",host);
            }
//            connection.setRequestMethod("HEAD"); // 使用HEAD请求，只获取响应头信息
            int responseCode = connection.getResponseCode();

            // 2xx 表示成功响应，即URL可正常访问
            return (responseCode >= 200 && responseCode < 300);
        } catch (IOException e) {
            // URL无法正常访问
            return false;
        }
    }

    public void addError(Integer kind,String message,Integer ruleId,String url){
         log.error("ruleId:{}  url:{} kind:{}  message:{}",ruleId,url,kind,message);
//        ReptileErrorDto reptileError=new ReptileErrorDto();
//        reptileError.setKind(kind);
//        reptileError.setMessage(message);
//        reptileError.setRuleId(ruleId);
//        reptileError.setUrl(url);
//        errorService.add(reptileError);
    }

    private String extractContent(Document doc, String rule) {
         if(StringUtils.isEmpty(rule)){
             return  null;
         }
         String value=null;
        try {
            if(StringUtils.isEmpty(rule)) {
                return null;
            }
            Element element = doc.select(rule).first();
            if(element!=null){
               value= element.attr("content");
               if(StringUtils.isEmpty(value)){
                   value= element.html();
               }
            }
            return value;
//            return (element != null) ? element.attr("content") : null;
        } catch (Exception e) {
            // 处理异常，例如记录日志
            log.error("获取内容错误 rele:{}",rule,e);
            return null;
        }
    }

    public  Long generate12DigitHash(String url,String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 选择合适的散列算法
            byte[] hash = md.digest(input.getBytes());
            BigInteger bigInt = new BigInteger(1, hash);
            Long twelveBitHash = bigInt.longValue() % (long) Math.pow(10, 12); // 取模以确保是12位整数

            return twelveBitHash+0L;
        } catch (NoSuchAlgorithmException e) {
           log.error("获取title错误 url:{},",url,e);
              return System.currentTimeMillis();
        }
    }

    /**
     * 判断照片是否正常，不正常就删除整个图集，重新导入
     * @param aid
     * @return
     */
    public  boolean  isCheckImage(Long aid,ReptileRule reptileRule){
        ImageDto finDto=new ImageDto();
        finDto.setPageSize(3);
        finDto.setPageNum(2);
        finDto.setAid(aid);
        List<Image> list = imageService.list(finDto);
        if(list==null && list.size()==0){
            return false;
        }else {
            for(Image image:list){
                if(!isURLValid(image.getSourceWeb()+image.getUrl(),reptileRule)){
                    imageService.delAlum(aid);
                    return false;
                }
            }
        }

        return true;
    }

    public void updatePage(ReptilePage reptilePage){
        //链式构建请求
        String updateReq = HttpRequest.post(reptileUrl + "/wiki/reptilePage/edit")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .body(JSONObject.toJSONString(reptilePage))//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
    }
}
