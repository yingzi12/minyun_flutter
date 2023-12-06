package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.service.IUserImageService;
import com.xinshijie.gallery.vo.ResuImageVo;
import com.xinshijie.gallery.vo.UserImageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 用户上传的图片 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserImageController", description = "后台- 用户上传的图片")
@RestController
@RequestMapping("/userImage")
public class UserImageController extends BaseController {

    @Autowired
    private IUserImageService userImageService;

    /**
     *  查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserImageVo> getInfo(@PathVariable("id") Long id) {
        UserImageVo vo = userImageService.getInfo(id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */

    @PostMapping("/select")
    public Result<Page<UserImageVo>> select(@RequestBody UserImageDto findDto) {
        Page<UserImageVo> vo = userImageService.selectPageUserImage(findDto);
        return Result.success(vo);
    }
}
