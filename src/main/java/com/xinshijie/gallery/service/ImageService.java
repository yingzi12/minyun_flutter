package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.dto.ImageDto;

import java.util.List;

public interface ImageService extends IService<Image> {
    List<Image> list(ImageDto dto);

    Integer count(ImageDto dto);

    Integer delAlum(Long aid);

    Integer addBatch(List<Image> list);

    List<Image> listAll(Long aid);

    Integer updateSourceUrl(Image dto);

    Integer delCfAid(Long aid);
}
