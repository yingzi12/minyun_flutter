package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.AlbumVip;
import com.xinshijie.gallery.dto.AlbumVipDto;
import com.xinshijie.gallery.service.IAlbumVipService;
import com.xinshijie.gallery.vo.AlbumVipVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户vip 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AlbumVipController", description = "后台- 用户vip")
@RestController
@RequestMapping("/AlbumVip")
public class AlbumVipController extends BaseController {

    @Autowired
    private IAlbumVipService albumVipService;

    /**
     *  添加
     *
     * @return
     */
    @PostMapping("/add")
    public Result<AlbumVip> add(@RequestBody AlbumVipDto dto) {
        AlbumVip vo = albumVipService.add(dto);
        return Result.success(vo);
    }

    /**
     *  删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = albumVipService.delById(id);
        return Result.success(vo);
    }


    /**
     *  修改
     *
     * @return
     */
    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody AlbumVipDto dto) {
        Integer vo = albumVipService.edit(dto);
        return Result.success(vo);
    }


    /**
     *  查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<AlbumVipVo> getInfo(@PathVariable("id") Long id) {
        AlbumVipVo vo = albumVipService.getInfo(id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */
    @PostMapping("/select")
    public Result<Page<AlbumVipVo>> select(@RequestBody AlbumVipDto findDto) {
        Page<AlbumVipVo> vo = albumVipService.selectPageAlbumVip(findDto);
        return Result.success(vo);
    }


}
