package com.xinshijie.gallery.service;

import com.xinshijie.gallery.vo.ReptilePage;
import com.xinshijie.gallery.vo.ReptileRule;

import java.util.List;

public interface IReptileImageService {

    void detail(String url, String imgUrl, ReptileRule reptileRule);

    void ayacData(Integer id);

    void singleData();

    void orderBySingle(ReptileRule reptileRule, List<ReptilePage> pageList);

    void singleLocalData();
}
