package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.service.IAlbumService;
import com.xinshijie.gallery.service.IReptileImageService;
import com.xinshijie.gallery.service.IUserCollectionService;
import com.xinshijie.gallery.vo.AlbumVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserIdNoLogin;

@CrossOrigin
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private IAlbumService albumService;

    @Autowired
    private IReptileImageService reptileImageService;
    @Autowired
    private IUserCollectionService userCollectionService;
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
        IPage<Album> list = albumService.list(dto);

        return Result.success(list.getRecords(), Integer.parseInt(String.valueOf(list.getTotal())));
    }

    @GetMapping("/random")
    public Result<List<Album>> random(@RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageSize == null) {
            pageSize = 8;
        }
        if (pageSize < 10) {
            pageSize = 8;
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
        IPage<Album> list = albumService.list(dto);

        return Result.success(list.getRecords(), Integer.parseInt(String.valueOf(list.getTotal())));
    }

    @GetMapping("/info")
    public Result<AlbumVo> info(@RequestParam("id") Integer id) {
        Integer userId = getUserIdNoLogin();

        AlbumVo albumVo = albumService.getInfo(id);
        albumVo.setIsCollection(2);
        if(userId!=null) {
            UserCollection userCollection = userCollectionService.getInfo(userId, id, 1);
            if(userCollection!=null){
                albumVo.setIsCollection(1);
            }
        }
        return Result.success(albumVo);
    }

    @GetMapping("/error")
    public Result<String> error(@RequestParam("id") Integer id) {
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


}
