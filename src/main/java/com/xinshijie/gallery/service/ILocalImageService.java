package com.xinshijie.gallery.service;

import com.xinshijie.gallery.domain.Album;

public interface ILocalImageService {

    void updateThread();

    void saveLocalAlbum(Album album);

    void saveAlbum(Album albumVo);
}
