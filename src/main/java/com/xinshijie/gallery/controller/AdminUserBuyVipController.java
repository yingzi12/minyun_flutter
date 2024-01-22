package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.domain.UserBuyVip;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserBuyVipDto;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.enmus.PaymentKindEnum;
import com.xinshijie.gallery.service.IUserBuyVipService;
import com.xinshijie.gallery.vo.UserBuyVipVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;


/**
 * <p>
 * 用户购买记录 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserBuyVipController", description = "后台- 用户购买记录")
@RestController
@RequestMapping("/admin/userBuyVip")
public class AdminUserBuyVipController extends BaseController {

    @Autowired
    private IUserBuyVipService userBuyVipService;

    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/add")
    public Result<UserBuyVip> add(@RequestBody UserBuyVipDto dto) {
        UserBuyVip vo = userBuyVipService.add(dto);
        return Result.success(vo);
    }

    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {

        Integer vo = userBuyVipService.delById(id);

        return Result.success(vo);
    }


    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserBuyVipDto dto) {
        Integer vo = userBuyVipService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<UserBuyVipVo> getInfo(@PathVariable("id") Long id) {
        UserBuyVipVo vo = userBuyVipService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserBuyVipVo>> select(UserBuyVipDto findDto) {
        Page<UserBuyVipVo> vo = userBuyVipService.selectPageUserBuyVip(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }


}
