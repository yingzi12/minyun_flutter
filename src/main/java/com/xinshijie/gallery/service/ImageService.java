package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService extends IService<Image> {
    List<Image> list(ImageDto dto);

    List<String> listUrl(Integer aid);

    Integer count(ImageDto dto);

    Integer delAlum(Integer aid);

    Integer addBatch(List<Image> list);

    List<Image> listAll(Integer aid);

    Integer updateSourceUrl(Image dto);

    Integer delCfAid(Integer aid);

    String saveUploadedFiles( Integer aid,  MultipartFile file);

}
