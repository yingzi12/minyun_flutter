package org.xinshijie.gallery.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;

import java.util.List;

@Mapper
public interface ImageMapper {
    List<Image> list(ImageDto dto);

    Integer count(ImageDto dto);

}
