package com.xinshijie.gallery;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.service.IAlbumService;
import com.xinshijie.gallery.service.ILocalImageService;
import com.xinshijie.gallery.service.IReptileImageService;
import com.xinshijie.gallery.vo.AlbumVo;
import com.xinshijie.gallery.vo.ReptileRule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = GalleryApplication.class)
public class ReptileMain {
    @Autowired
    private IReptileImageService reptileService;
    @Autowired
    private ILocalImageService localImageService;
    @Autowired
    private IAlbumService albumService;

    @Test
    public void detail() {

        //链式构建请求
        String result = HttpRequest.get("https://admin.aiavr.com/wiki/reptileRule/getInfo/10")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getIntValue("code") != 200) {
            throw new ServiceException(" reptileRuleVo 出现异常");
        }
        ReptileRule reptileRule = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);
        Document doc = reptileService.requestUrl("https://everia.club/category/japan/page/2/", reptileRule, 0);
        Element body = doc.body();
        Element cont = body.select(reptileRule.getStoryPageRule()).first();
        Elements elementList = cont.select(reptileRule.getStoryPageGroupRule());
        if (elementList != null) {
            reptileService.threadElment(elementList, reptileRule);
        }
    }

    @Test
    public void test10() {
        reptileService.ayacDataThread(10);
    }

    @Test
    public void testDetail() {
        String url="";
        String imgUrl="";
        String result = HttpRequest.get(  "https://admin.aiavr.uk/wiki/reptileRule/getInfo/")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getIntValue("code") != 200) {
            throw new ServiceException(" reptileRuleVo 出现异常");
        }
        ReptileRule reptileRuleVo = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);

        reptileService.detail(url,imgUrl,reptileRuleVo);
    }

//    void detail(String url, String imgUrl, ReptileRule reptileRule);

    @Test
    public void info() {
        AlbumVo albumVo = albumService.getInfo(56597);
        Album album = new Album();
        BeanUtils.copyProperties(albumVo, album);
        localImageService.saveAlbum(album);
    }
}
