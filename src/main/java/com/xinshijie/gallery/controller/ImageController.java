package com.xinshijie.gallery.controller;

import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.dao.Image;
import com.xinshijie.gallery.dto.ImageDto;
import com.xinshijie.gallery.service.ImageService;
import com.xinshijie.gallery.vo.ResuImageVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/list")
    public Result<List<Image>> list(ImageDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        dto.setPageSize(6);
        dto.setOffset(dto.getPageSize()*(dto.getPageNum()-1));
        Integer total = imageService.count(dto);

        List<Image> list = imageService.list(dto);
        return Result.success(list, total);
    }

    @GetMapping("/count")
    public Result<Integer> count(ImageDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        dto.setPageSize(6);
        Integer total = imageService.count(dto);
        return Result.success(total);
    }


}
