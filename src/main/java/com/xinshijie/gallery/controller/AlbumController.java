package com.xinshijie.gallery.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xinshijie.gallery.common.Result;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.service.ILocalImageService;
import org.xinshijie.gallery.service.IReptileImageService;
import org.xinshijie.gallery.service.ImageService;
import org.xinshijie.gallery.vo.AlbumVo;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private IReptileImageService reptileImageService;
    @Autowired
    private ILocalImageService localImageService;

    @GetMapping("/list")
    public Result<List<Album>> list(AlbumDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        if (StringUtils.isEmpty(dto.getTitle())) {
            dto.setTitle(null);
        }
        dto.setNotSource("https://image.51x.uk/xinshijie");
        dto.setPageSize(30);
        dto.setOffset(dto.getPageSize()*(dto.getPageNum()-1));
        Integer total = albumService.count(dto);
        List<Album> list = albumService.list(dto);

        return Result.success(list, total);
    }

    @GetMapping("/random")
    public Result<List<Album>> random() {
        List<Album> list = albumService.findRandomStories(8);

        return Result.success(list);
    }

    @GetMapping("/listSee")
    public Result<List<Album>> listSee(AlbumDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        if (StringUtils.isEmpty(dto.getTitle())) {
            dto.setTitle(null);
        }
        dto.setOrder("count_see");
        dto.setPageSize(30);
        dto.setOffset(dto.getPageSize()*(dto.getPageNum()-1));
        Integer total = albumService.count(dto);
        List<Album> list = albumService.list(dto);

        return Result.success(list, total);
    }

    @GetMapping("/info")
    public Result<AlbumVo> list(@RequestParam("id") Long id) {
        AlbumVo albumVo = albumService.getInfo(id);
        return Result.success(albumVo);
    }

    @GetMapping("/error")
    public Result<String> error(@RequestParam("id") Long id) {
        albumService.updateError(id);
        return Result.success("");
    }

    @GetMapping("/cj")
    public Result<String> cj(Integer id) {
        reptileImageService.ayacData(id);
        return Result.success("ss");
    }

    @GetMapping("/singleLocal")
    public Result<String> singleLocalData() {
        reptileImageService.singleLocalData();
        return Result.success("ss");
    }

    @GetMapping("/updateThread")
    public Result<String> updateThread() {
        localImageService.updateThread();
        return Result.success("ss");
    }

}
