package org.xinshijie.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinshijie.gallery.common.Result;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.service.ImageService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping("/list")
    public Result<List<Album>> list(AlbumDto dto) {
        if(dto.getPageNum()==null){
            dto.setPageNum(1);
        }
        dto.setPageSize(30);
        Integer total = albumService.count(dto);
        List<Album> list = albumService.list(dto);

        return Result.success(list,total);
    }
}
