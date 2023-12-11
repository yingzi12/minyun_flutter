package com.xinshijie.gallery.controller;

import cn.hutool.core.util.HashUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.UserVideoDto;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserVideoService;
import com.xinshijie.gallery.vo.UserVideoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserIdNoLogin;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserVideoController", description = "后台- ")
@RestController
@RequestMapping("/userVideo")
public class UserVideoController extends BaseController {

    @Autowired
    private IUserVideoService userVideoService;

    @Autowired
    private IUserAlbumService userAlbumService;
    /**
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserVideoVo> getInfo(@PathVariable("id") Long id) {
        UserVideoVo vo = userVideoService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserVideoVo>> select(UserVideoDto findDto) {
        Page<UserVideoVo> vo = userVideoService.selectPageUserVideo(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(vo.getTotal() + ""));
    }

    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/listAll")
    public Result<List<UserVideo>> listAll(@RequestParam("aid") Integer aid,@RequestParam("isFree") Integer isFree) {
        Integer userId=getUserIdNoLogin();

        if (isFree == null ||
                (isFree == 2 && userAlbumService.isCheck(userId, aid))
        ) {
            throw new ServiceException(ResultCodeEnum.NOT_BUY);
        }
        List<UserVideo> vo = userVideoService.selectAllAid(aid, isFree);
        return Result.success(vo, vo.size());
    }

}
