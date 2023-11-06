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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xinshijie.gallery.common.ServiceException;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.service.IReptileImageService;
import org.xinshijie.gallery.service.ImageService;
import org.xinshijie.gallery.vo.ReptilePage;
import org.xinshijie.gallery.vo.ReptileRule;

import java.math.BigInteger;
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

     public void ayacData(){
         //链式构建请求
         String result = HttpRequest.post("url")
                 .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                 .timeout(20000)//超时，毫秒
                 .execute().body();
         JSONObject jsonObject=JSONObject.parseObject(result);
         ReptileRule reptileRuleVo = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);

         //链式构建请求
         String result2 = HttpRequest.post("url")
                 .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                 .timeout(20000)//超时，毫秒
                 .execute().body();
         JSONObject pageJson=JSONObject.parseObject(result2);
         List<ReptilePage> pageList = JSON.parseArray(pageJson.getJSONArray("data").toJSONString(), ReptilePage.class);
     }

    @Async
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
                String url = pageVo.getPageUrl().replaceAll("<地址>",i+"" );
                try {

                    Document doc = requestUrl(url,reptileRule);
                    Element body = doc.body();
                    Element cont = body.select(reptileRule.getStoryPageRule()).first();
                    Elements storyList = cont.select(reptileRule.getStoryPageGroupRule());
                    if (storyList != null) {
                        for (Element story : storyList) {
                            Element hrefElement = story.select(reptileRule.getStoryPageHrefRule()).first();
                            if(hrefElement !=null) {
                                String detailUrl = hrefElement.attr("href");
                                detail(detailUrl, reptileRule);
                            }else{
                                addError(1,story.html(),reptileRule.getId(),url);
                            }
                        }
                    }
                    if(i%10==0){
                        System.out.println("---------------++++++++++++++++++++++--------------------------");
//                        ReptilePage reptilePage=new ReptilePage();
//                        reptilePage.setId(pageVo.getId());
//                        reptilePage.setNowPage(i);
//                        pageService.updateById(reptilePage);
                    }
                    reptileRule.setStatus(2);
                } catch (Exception e) {
                    addError(1,e.getMessage(),reptileRule.getId(),url);
                    log.error("出现异常 方法:{} reId:{} message","orderByExecutor",reptileRule.getId(),e);
                }
            }
//            ReptilePage reptilePage=new ReptilePage();
//            reptilePage.setId(pageVo.getId());
//            reptilePage.setNowPage(pageVo.getPageTotal());
//            pageService.updateById(reptilePage);
        }
        reptileRule.setEndTime(LocalDateTime.now());
//        reptileRuleService.updateById(reptileRule);
    }
    public void detail(String url, ReptileRule reptileRule ){
        String detailUrl=url;
        try {
            if(StringUtils.isNotEmpty(reptileRule.getStoryUrl())){
                detailUrl=reptileRule.getStoryUrl()+url;
            }else {
                if (reptileRule.getStoryUrlType() != null) {
                    detailUrl = reptileRule.getStoryUrl() + url;
                }
            }
            Document doc = requestUrl(detailUrl,reptileRule);

            String title = extractContent(doc, reptileRule.getTitleRule());
            String gril = extractContent(doc, reptileRule.getAuthorRule());
            Long hash = generate12DigitHash(title);
            Album album= albumService.getInfoBytitle(title);

            if(album==null){
                album=new Album();
                album.setCountError(0);
                album.setCountSee(0L);
                album.setCreateTime(LocalDate.now().toString());
                album.setGril(gril);
                album.setHash(hash);
            }else{
                if(hash.equals(album.getHash())||StringUtils.isEmpty(album.getGril())){
                    album.setHash(hash);
                    if(StringUtils.isNotEmpty(gril)) {
                        album.setGril(gril);
                    }
                    albumService.updateById(album);
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
                        if(StringUtils.isEmpty(reptileRule.getPageUrl())){
                            pageUrl=reptileRule.getPageUrl()+pageUrl;
                        }
                        addImageList(pageUrl,album,reptileRule);
                    }
                }
            }


//            Element chapterListContent = body.select(reptileRule.getChapterListRule()).first();
//            if (chapterListContent != null) {
//                    Elements imageListElement = chapterListContent.select(reptileRule.getChapterGroupRule());
//                    List<Image> iamgeBatchInsertList = new CopyOnWriteArrayList<>();
//                    for (Element element : imageListElement) {
//                        String imageUrlSource = element.select(reptileRule.getChapterUrlRule()).first().attr("href");
//                        if (StringUtils.isNotEmpty(imageUrlSource)){
//                            Image image=new Image();
//                            image.setAid(album.getId());
//                            image.setSourceWeb(imageUrlSource);
//                            image.setUrl(imageUrlSource);
//                            iamgeBatchInsertList.add(image);
//                        }
//                    }
//                    imageService.saveBatch(iamgeBatchInsertList);
//                    iamgeBatchInsertList.clear();
//            }

        } catch (Exception e) {
            addError(1,e.getMessage(),reptileRule.getId(),detailUrl);

            log.error("线程名-地址：{}，  href:{}",Thread.currentThread().getName(),detailUrl,e);
        }
    }

    public void addImageList(String detailUrl,Album album,ReptileRule reptileRule){
        Document doc = requestUrl(detailUrl,reptileRule);
        Element body = doc.body();

        Element chapterListContent = body.select(reptileRule.getChapterListRule()).first();
        if (chapterListContent != null) {
            Elements imageListElement = chapterListContent.select(reptileRule.getChapterGroupRule());
            List<Image> iamgeBatchInsertList = new CopyOnWriteArrayList<>();
            for (Element element : imageListElement) {
                String imageUrlSource = element.select(reptileRule.getChapterUrlRule()).first().attr("href");
                if (StringUtils.isNotEmpty(imageUrlSource)){
                    Image image=new Image();
                    image.setAid(album.getId());
                    image.setSourceWeb(imageUrlSource);
                    image.setUrl(imageUrlSource);
                    iamgeBatchInsertList.add(image);
                }
            }
            imageService.saveBatch(iamgeBatchInsertList);
            iamgeBatchInsertList.clear();
        }
    }
    public Document requestUrl(String url,ReptileRule reptileRule){
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
        }
        return null;
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
        try {
            if(StringUtils.isEmpty(rule)) {
                return null;
            }
            Element element = doc.select(rule).first();
            return (element != null) ? element.attr("content") : null;
        } catch (Exception e) {
            // 处理异常，例如记录日志
            log.error("线程名-地址：{}， extractContent",Thread.currentThread().getName(),e);
            return null;
        }
    }

    public static Long generate12DigitHash(String input) {
        try {
            // Create an MD5 message digest
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert the MD5 hash to a positive BigInteger
            BigInteger bigInt = new BigInteger(1, messageDigest);

            // Format the BigInteger as a 12-digit number
            String hash = String.format("%012d", bigInt);

            return Long.parseLong(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }
}
