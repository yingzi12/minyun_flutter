package com.xinshijie.gallery.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.service.IAlbumService;
import com.xinshijie.gallery.vo.AlbumVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;


/**
 * <p>
 * 用户创建的 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminAlbumController", description = "后台- 用户创建的")
@RestController
@RequestMapping("/addAlbum")
public class AdminAlbumController extends BaseController {

    @Autowired
    private IAlbumService albumService;
    @Value("${image.sourceWeb}")
    private String imageSourceWeb;
    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody Album dto) {
        dto.setCreateTime(LocalDate.now().toString());
        dto.setUpdateTime(LocalDate.now().toString());
        dto.setCountSee(RandomUtil.randomLong(100));
        dto.setNumberPhotos(0);
        dto.setNumberVideo(0);
        dto.setIsFree(1);
        if(StringUtils.isNotEmpty(dto.getImgUrl())) {
            dto.setSourceUrl(dto.getImgUrl());
            dto.setSourceWeb(imageSourceWeb);
        }
        albumService.add(dto);
        return Result.success("success");
    }

    @GetMapping("/list")
    public Result<List<Album>> list(AlbumDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        if (StringUtils.isEmpty(dto.getTitle())) {
            dto.setTitle(null);
        }
        dto.setIsFree(1);
        dto.setPageSize(30);
        dto.setOffset(dto.getPageSize() * (dto.getPageNum() - 1));
        IPage<Album> list = albumService.list(dto);

        return Result.success(list.getRecords(), Integer.parseInt(String.valueOf(list.getTotal())));
    }


    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Integer id) {
        Integer vo = albumService.delById( id);
        return Result.success(vo);
    }


    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/edit")
    public Result<Boolean> edit(@RequestBody Album dto) {
        Boolean vo = albumService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<AlbumVo> getInfo(@PathVariable("id") Integer id) {
        AlbumVo vo = albumService.getInfo(id);
        return Result.success(vo);
    }




    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String url = albumService.saveUploadedFiles(file);
        return Result.success(url);
    }


}
