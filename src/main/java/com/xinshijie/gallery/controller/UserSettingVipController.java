package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserSettingVipDto;
import com.xinshijie.gallery.service.IUserSettingVipService;
import com.xinshijie.gallery.vo.UserSettingVipVo;
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
@Tag(name = " UserSettingVipController", description = "后台- 用户vip")
@RestController
@RequestMapping("/userSettingVip")
public class UserSettingVipController extends BaseController {

    @Autowired
    private IUserSettingVipService userSettingVipService;

    /**
     *  查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserSettingVipVo> getInfo(@PathVariable("id") Long id) {
        UserSettingVipVo vo = userSettingVipService.getInfo(id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */

    @GetMapping("/list")
    public Result<Page<UserSettingVipVo>> select( UserSettingVipDto findDto) {
        Page<UserSettingVipVo> vo = userSettingVipService.selectPageUserSettingVip(findDto);
        return Result.success(vo);
    }


}
