package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.UserCollectionDto;
import com.xinshijie.gallery.service.IUserCollectionService;
import com.xinshijie.gallery.vo.UserCollectionVo;
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
@Tag(name = " UserCollectionController", description = "后台- 用户收藏的album")
@RestController
@RequestMapping("/userCollection")
public class UserCollectionController extends BaseController {

    @Autowired
    private IUserCollectionService userCollectionService;

    /**
     * 获取
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<UserCollectionVo> getInfo(@PathVariable("id") Long id) {
        UserCollectionVo vo = userCollectionService.getInfo(id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */

    @PostMapping("/select")
    public Result<Page<UserCollectionVo>> select(@RequestBody UserCollectionDto findDto) {
        Page<UserCollectionVo> vo = userCollectionService.selectPageUserCollection(findDto);
        return Result.success(vo);
    }

}
