package com.xinshijie.gallery.service;

import com.xinshijie.gallery.vo.ReptilePage;
import com.xinshijie.gallery.vo.ReptileRule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public interface IReptileImageService {

    void detail(String url, String imgUrl, ReptileRule reptileRule);

    void ayacDataThread(Integer id);

    void singleData();

    void singleDataThread();

    void orderBySingle(ReptileRule reptileRule, List<ReptilePage> pageList);
    void threadElment(Elements elementList, ReptileRule reptileRule);
    void singleLocalData();

    boolean isImageUrlValid(String imageUrl, int count);

    Document requestUrl(String url, ReptileRule reptileRule, Integer replyCount);
}
