package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserSettingVipDto;
import com.xinshijie.gallery.enmus.VipStatuEnum;
import com.xinshijie.gallery.service.IUserSettingVipService;
import com.xinshijie.gallery.vo.UserSettingVipVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserSettingVip> getInfo(@PathVariable("id") Integer id) {
        UserSettingVip vo = userSettingVipService.getInfo(null, id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */

    @GetMapping("/list")
    public Result<List<UserSettingVipVo>> select(UserSettingVipDto findDto) {
        if(findDto.getUserId() ==null){
            throw new ServiceException(ResultCodeEnum.PARAMS_NOT_COMPLETE);
        }
        findDto.setStatus(VipStatuEnum.NORMAL.getCode());
        IPage<UserSettingVipVo> vo = userSettingVipService.selectPageUserSettingVip(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }


}
