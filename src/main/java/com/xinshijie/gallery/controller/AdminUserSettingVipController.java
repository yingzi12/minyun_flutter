package com.xinshijie.gallery.controller;

import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserSettingVipDto;
import com.xinshijie.gallery.service.IUserSettingVipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;


/**
 * <p>
 * 用户vip 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserSettingVipController", description = "后台- 用户vip")
@RestController
@RequestMapping("/admin/userSettingVip")
public class AdminUserSettingVipController extends BaseController {

    @Autowired
    private IUserSettingVipService userSettingVipService;

    /**
     * 添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserSettingVip> add(@RequestBody UserSettingVipDto dto) {
        dto.setUserId(getUserId());
        dto.setUserName(getUserName());
        UserSettingVip vo = userSettingVipService.add(dto);
        return Result.success(vo);
    }

    /**
     * 删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userSettingVipService.delById(getUserId(), id);
        return Result.success(vo);
    }


    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserSettingVipDto dto) {
        dto.setUserId(getUserId());
        dto.setUserName(getUserName());
        Integer vo = userSettingVipService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserSettingVip> getInfo(@PathVariable("id") Long id) {
        UserSettingVip vo = userSettingVipService.getInfo(getUserId(), id);
        return Result.success(vo);
    }

    @GetMapping(value = "/updateStatus")
    public Result<Boolean> updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        Boolean vo = userSettingVipService.updateStatus(getUserId(), id, status);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */

    @GetMapping("/list")
    public Result<List<UserSettingVip>> list(UserSettingVipDto findDto) {
        findDto.setUserId(getUserId());
        List<UserSettingVip> vo = userSettingVipService.selectUserSettingVipList(findDto);
        return Result.success(vo, vo.size());
    }
}
