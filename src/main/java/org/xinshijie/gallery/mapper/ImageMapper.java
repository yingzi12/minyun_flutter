package org.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;

import java.util.List;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    List<Image> list(ImageDto dto);

    Integer count(ImageDto dto);

}
