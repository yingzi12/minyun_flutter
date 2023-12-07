package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserVip;
import com.xinshijie.gallery.dto.UserVipDto;
import com.xinshijie.gallery.service.IUserVipService;
import com.xinshijie.gallery.vo.UserVipVo;
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
@Tag(name = " UserVipController", description = "后台- 用户vip")
@RestController
@RequestMapping("/userVip")
public class UserVipController extends BaseController {

    @Autowired
    private IUserVipService userVipService;

    /**
     *  查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserVipVo> getInfo(@PathVariable("id") Long id) {
        UserVipVo vo = userVipService.getInfo(id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */
    @PostMapping("/select")
    public Result<Page<UserVipVo>> select(@RequestBody UserVipDto findDto) {
        Page<UserVipVo> vo = userVipService.selectPageUserVip(findDto);
        return Result.success(vo);
    }

}
