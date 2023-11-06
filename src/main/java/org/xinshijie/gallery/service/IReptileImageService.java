package org.xinshijie.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.xinshijie.gallery.vo.ReptilePage;
import org.xinshijie.gallery.vo.ReptileRule;

import java.util.List;

public interface IReptileImageService {

    void detail(String url, ReptileRule reptileRule );

    void ayacData(Integer id);

    void orderBySingle(ReptileRule reptileRule, List<ReptilePage> pageList );

}
