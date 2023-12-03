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
@RequestMapping("/UserImage")
public class UserImageController extends BaseController {

    @Autowired
    private IUserImageService userImageService;

    /**
     *  添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserImage> add(@RequestBody UserImageDto dto) {
        UserImage vo = userImageService.add(dto);
        return Result.success(vo);
    }

    /**
     *  删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userImageService.delById(id);
        return Result.success(vo);
    }


    /**
     *  修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserImageDto dto) {
        Integer vo = userImageService.edit(dto);
        return Result.success(vo);
    }


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


    @PostMapping("/uploadImages")
    public List<ResuImageVo> uploadEditFiles(@RequestParam("uploads") MultipartFile[] files) {
        List<ResuImageVo> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            // You can still compute MD5 or perform other operations on each file
            // String md5 = DigestUtils.md5Hex(file.getInputStream());

            // Process each file and add the result to the uploadedFiles list
            // For example, you can save each file and create a new ResuImageVo object for it
            // ResuImageVo resuImage = saveFile(file);
            // uploadedFiles.add(resuImage);

            // For now, let's just add a placeholder result for each file
//            uploadedFiles.add(new ResuImageVo(file.getOriginalFilename()));
        }

        return uploadedFiles;
    }
}
