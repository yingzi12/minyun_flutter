package com.xinshijie.gallery.service;

import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.vo.ReptilePage;
import com.xinshijie.gallery.vo.ReptileRule;

import java.util.List;

public interface ILocalImageService {

    void updateThread();

    void saveLocalAlbum(Album album);

    void saveAlbum(Album albumVo);
}
