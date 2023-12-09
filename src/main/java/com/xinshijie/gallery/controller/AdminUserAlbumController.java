package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.vo.UserAlbumVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
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
        dto.setUserId(getUserId());
        dto.setUserName(getUserName());
        UserAlbum vo = userAlbumService.add(dto);

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
     *  修改是否免费
     *
     * @return
     */
    @GetMapping(value = "/updateCharge")
    public Result<Boolean> updateCharge(@PathVariable("id") Long id,@PathVariable("charge") Integer charge,@PathVariable("price") Double price,@PathVariable("vipPrice") Double vipPrice) {
        Boolean vo = userAlbumService.updateCharge(getUserId(),id,charge,price,vipPrice);
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
    @GetMapping("/list")
    public Result<List<UserAlbumVo>> select( UserAlbumDto findDto) {
        findDto.setUserId(getUserId());
        Page<UserAlbumVo> vo = userAlbumService.selectPageUserAlbum(findDto);
        return Result.success(vo.getRecords(),Integer.parseInt(vo.getTotal()+""));
    }


    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("system update");
        String url = userAlbumService.saveUploadedFiles(getUserId(),file);
        return Result.success(url);
    }
}
