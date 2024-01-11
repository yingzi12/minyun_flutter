package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserImageService;
import com.xinshijie.gallery.vo.UserImageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;


/**
 * <p>
 * 用户上传的图片 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserImageController", description = "后台- 用户上传的图片")
@RestController
@RequestMapping("/admin/userImage")
public class AdminUserImageController extends BaseController {

    @Autowired
    private IUserImageService userImageService;
    @Autowired
    private IUserAlbumService userAlbumService;

    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/remove")
    public Result<Integer> del(@RequestParam("aid") Long aid,@RequestParam("id") Long id) {
        UserAlbum userAlbum = userAlbumService.getById(aid);
        if (userAlbum == null) {
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        if (userAlbum.getStatus() != AlbumStatuEnum.NORMAL.getCode()) {
            throw new ServiceException(ResultCodeEnum.NOT_POST_STATUS);
        }
        if (!userAlbum.getUserId().equals(getUserId())) {
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        Integer vo = userImageService.delById(getUserId(), id);
        return Result.success(vo);
    }

    @GetMapping(value = "/updateIsFree")
    public Result<Integer> updateIsFree(@RequestParam("id") Long id, @RequestParam("isFree") Integer isFree) {
        UserAlbum userAlbum = userAlbumService.getById(id);
        if (userAlbum == null) {
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        if (userAlbum.getStatus() != AlbumStatuEnum.NORMAL.getCode()) {
            throw new ServiceException(ResultCodeEnum.NOT_POST_STATUS);
        }
        Integer vo = userImageService.updateIsFree(getUserId(), id, isFree);
        return Result.success(vo);
    }

    /**
     * 修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserImage dto) {
        Integer vo = userImageService.edit(dto);
        return Result.success(vo);
    }

    /**
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserImageVo> getInfo(@PathVariable("id") Long id) {
        UserImageVo vo = userImageService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */

    @GetMapping("/list")
    public Result<List<UserImageVo>> select(UserImageDto findDto) {
        findDto.setUserId(getUserId());
        if(findDto.getAid()==null){
            throw  new ServiceException(ResultCodeEnum.PARAMS_IS_NULL);
        }
        IPage<UserImageVo> vo = userImageService.selectPageUserImage(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile, @RequestParam("aid") Integer aid, @RequestParam("isFree") Integer isFree) {
        log.info("upload aid:" + aid);
        String url = userImageService.saveUploadedFiles(getUserId(), aid, isFree, uploadfile);
        return Result.success(url);
    }

}
