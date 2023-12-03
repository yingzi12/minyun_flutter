package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.AlbumCollection;
import com.xinshijie.gallery.dto.AlbumCollectionDto;
import com.xinshijie.gallery.service.IAlbumCollectionService;
import com.xinshijie.gallery.vo.AlbumCollectionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户收藏的album 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AlbumCollectionController", description = "后台- 用户收藏的album")
@RestController
@RequestMapping("/AlbumCollection")
public class AlbumCollectionController extends BaseController {

    @Autowired
    private IAlbumCollectionService albumCollectionService;

    /**
     *  添加
     *
     * @return
     */
    @PostMapping("/add")
    public Result<AlbumCollection> add(@RequestBody AlbumCollectionDto dto) {
        AlbumCollection vo = albumCollectionService.add(dto);
        return Result.success(vo);
    }

    /**
     *  删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = albumCollectionService.delById(id);
        return Result.success(vo);
    }


    /**
     *  修改
     *
     * @return
     */
    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody AlbumCollectionDto dto) {
        Integer vo = albumCollectionService.edit(dto);
        return Result.success(vo);
    }


    /**
     *  查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<AlbumCollectionVo> getInfo(@PathVariable("id") Long id) {
        AlbumCollectionVo vo = albumCollectionService.getInfo(id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */
    @PostMapping("/select")
    public Result<Page<AlbumCollectionVo>> select(@RequestBody AlbumCollectionDto findDto) {
        Page<AlbumCollectionVo> vo = albumCollectionService.selectPageAlbumCollection(findDto);
        return Result.success(vo);
    }


}
