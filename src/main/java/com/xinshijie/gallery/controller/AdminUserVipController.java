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

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;


/**
 * <p>
 * 用户vip 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserVipController", description = "后台- 用户vip")
@RestController
@RequestMapping("/admin/userVip")
public class AdminUserVipController extends BaseController {

    @Autowired
    private IUserVipService userVipService;

    /**
     * 添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserVip> add(@RequestBody UserVipDto dto) {
        UserVip vo = userVipService.add(dto);
        return Result.success(vo);
    }

    /**
     * 删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userVipService.delById(id);
        return Result.success(vo);
    }


    /**
     * 修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserVipDto dto) {
        Integer vo = userVipService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserVip> getInfo(@PathVariable("id") Integer userId) {
        UserVip vo = userVipService.getInfo(getUserId(), userId);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<Page<UserVipVo>> list(UserVipDto findDto) {
        findDto.setUserId(getUserId());
        Page<UserVipVo> vo = userVipService.selectPageUserVip(findDto);
        return Result.success(vo );
    }

}
