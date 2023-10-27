package org.xinshijie.gallery.service;

import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;

import java.util.List;

public interface ImageService {
    List<Image> list(ImageDto dto);

    Integer count(ImageDto dto);
}
