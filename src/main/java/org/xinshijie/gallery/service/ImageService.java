package org.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;

import java.util.List;

public interface ImageService  extends IService<Image> {
    List<Image> list(ImageDto dto);

    Integer count(ImageDto dto);

    Integer delAlum(Long aid);

}
