package com.xinshijie.gallery.controller;

import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.service.AlbumService;
import com.xinshijie.gallery.service.IReptileImageService;
import com.xinshijie.gallery.vo.AlbumVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private IReptileImageService reptileImageService;

    @GetMapping("/list")
    public Result<List<Album>> list(AlbumDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        if (StringUtils.isEmpty(dto.getTitle())) {
            dto.setTitle(null);
        }
        dto.setPageSize(30);
        dto.setOffset(dto.getPageSize() * (dto.getPageNum() - 1));
        Integer total = albumService.count(dto);
        List<Album> list = albumService.list(dto);

        return Result.success(list, total);
    }

    @GetMapping("/random")
    public Result<List<Album>> random(@RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageSize == null) {
            pageSize = 8;
        }
        if (pageSize < 10) {
            pageSize = 9;
        }
        List<Album> list = albumService.findRandomStories(pageSize);

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
        dto.setOffset(dto.getPageSize() * (dto.getPageNum() - 1));
        Integer total = albumService.count(dto);
        List<Album> list = albumService.list(dto);

        return Result.success(list, total);
    }

    @GetMapping("/info")
    public Result<AlbumVo> info(@RequestParam("id") Long id) {
        AlbumVo albumVo = albumService.getInfo(id);
//        Album album=new Album();
//        BeanUtils.copyProperties(albumVo,album);
//        localImageService.saveLocalAlbum(album);
        return Result.success(albumVo);
    }

    @GetMapping("/error")
    public Result<String> error(@RequestParam("id") Long id) {
        albumService.updateError(id);
        return Result.success("");
    }

    @GetMapping("/cj")
    public Result<String> cj(Integer id) {
        reptileImageService.ayacDataThread(id);
        return Result.success("ss");
    }

    @GetMapping("/singleLocal")
    public Result<String> singleLocalData() {
        reptileImageService.singleDataThread();
        return Result.success("ss");
    }

//    @GetMapping("/updateThread")
//    public Result<String> updateThread() {
//        localImageService.updateThread();
//        return Result.success("ss");
//    }

//    @GetMapping("/info2")
//    public Result<String> info2(@RequestParam("id")Long id) {
//        AlbumVo albumVo = albumService.getInfo(id);
//        Album album=new Album();
//        BeanUtils.copyProperties(albumVo,album);
//        localImageService.saveLocalAlbum(album);
//        return Result.success("ss");
//    }

}
