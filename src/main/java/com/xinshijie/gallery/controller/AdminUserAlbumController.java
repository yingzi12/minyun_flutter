package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.vo.UserAlbumVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
@Tag(name = " AdminUserAlbumController", description = "后台- 用户创建的")
@RestController
@RequestMapping("/admin/userAlbum")
public class AdminUserAlbumController extends BaseController {

    @Autowired
    private IUserAlbumService userAlbumService;

    /**
     *  添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserAlbum> add(@RequestBody UserAlbumDto dto) {
        UserAlbum vo = userAlbumService.add(dto);
        vo.setUserId(getUserId());
        vo.setUserName(getUserName());
        return Result.success(vo);
    }

    /**
     *  删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {

        Integer vo = userAlbumService.delById(getUserId(),id);
        return Result.success(vo);
    }


    /**
     *  修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserAlbumDto dto) {
        dto.setUserId(getUserId());
        Integer vo = userAlbumService.edit(dto);
        return Result.success(vo);
    }


    /**
     *  查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<UserAlbum> getInfo(@PathVariable("id") Long id) {
        UserAlbum vo = userAlbumService.getInfo(getUserId(),id);
        return Result.success(vo);
    }

    /**
     *  修改vip
     *
     * @return
     */
    @GetMapping(value = "/updateVip")
    public Result<Boolean> updateVip(@PathVariable("id") Long id,@PathVariable("isVip") Integer isVip) {
        Boolean vo = userAlbumService.updateVip(getUserId(),id,isVip);
        return Result.success(vo);
    }
    /**
     *  修改是否免费
     *
     * @return
     */
    @GetMapping(value = "/updateFree")
    public Result<Boolean> updateFree(@PathVariable("id") Long id,@PathVariable("isFree") Integer isFree,@PathVariable("price") Double price) {
        Boolean vo = userAlbumService.updateFree(getUserId(),id,isFree,price);
        return Result.success(vo);
    }

    @GetMapping(value = "/updatePrice")
    public Result<Boolean> updatePrice(@PathVariable("id") Long id,@PathVariable("price") Double price) {
        Boolean vo = userAlbumService.updatePrice(getUserId(),id,price);
        return Result.success(vo);
    }

    @GetMapping(value = "/updateStatus")
    public Result<Boolean> updateStatus(@PathVariable("id") Long id,@PathVariable("status") Integer status) {
        Boolean vo = userAlbumService.updateStatus(getUserId(),id,status);
        return Result.success(vo);
    }

    /**
     *  查询
     *
     * @return
     */
    @PostMapping("/select")
    public Result<Page<UserAlbumVo>> select(@RequestBody UserAlbumDto findDto) {
        findDto.setUserId(getUserId());
        Page<UserAlbumVo> vo = userAlbumService.selectPageUserAlbum(findDto);
        return Result.success(vo);
    }


    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("system update");
        String url = userAlbumService.saveUploadedFiles(getUserId(),file);
        return Result.success(url);
    }
}
