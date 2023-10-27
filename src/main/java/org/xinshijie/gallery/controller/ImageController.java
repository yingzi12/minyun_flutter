package org.xinshijie.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinshijie.gallery.common.Result;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.service.ImageService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/list")
    public Result<List<Image>> list(ImageDto dto) {
        if(dto.getPageNum()==null){
            dto.setPageNum(1);
        }
        dto.setPageSize(3);
        Integer total = imageService.count(dto);

        List<Image> list = imageService.list(dto);
        return Result.success(list,total);
    }
}
