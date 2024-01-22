package com.xinshijie.gallery.controller;

import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserSettingVipDto;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.enmus.PaymentKindEnum;
import com.xinshijie.gallery.enmus.VipStatuEnum;
import com.xinshijie.gallery.service.IPaymentOrderService;
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
    @Autowired
    private IPaymentOrderService paymentOrderService;
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
    public Result<Integer> del(@PathVariable("id") Integer id) {
        UserSettingVip settingVip=userSettingVipService.getInfo(getUserId(),id);
        if(settingVip!=null){
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        if(settingVip.getStatus()== VipStatuEnum.NORMAL.getCode()){
            throw new ServiceException(ResultCodeEnum.USER_ALBUM_STATUS_ERROR);
        }
        //判断是否是否已经购买
        PaymentOrder paymentOrder = paymentOrderService.selectByDonePay(getUserId(), PaymentKindEnum.USER_MEMBER.getCode(), settingVip.getId());
        if(paymentOrder!=null){
            throw new ServiceException(ResultCodeEnum.USER_ALBUM_SELL_ERROR);
        }
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
        UserSettingVip settingVip=userSettingVipService.getInfo(getUserId(),dto.getId());
        if(settingVip!=null){
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        if(settingVip.getStatus()== VipStatuEnum.NORMAL.getCode()){
            throw new ServiceException(ResultCodeEnum.USER_ALBUM_STATUS_ERROR);
        }
        //判断是否是否已经购买
        PaymentOrder paymentOrder = paymentOrderService.selectByDonePay(getUserId(), PaymentKindEnum.USER_MEMBER.getCode(), settingVip.getId());
        if(paymentOrder!=null){
            throw new ServiceException(ResultCodeEnum.USER_ALBUM_SELL_ERROR);
        }
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
    public Result<UserSettingVip> getInfo(@PathVariable("id") Integer id) {
        UserSettingVip vo = userSettingVipService.getInfo(getUserId(), id);
        return Result.success(vo);
    }

    @GetMapping(value = "/updateStatus")
    public Result<Boolean> updateStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
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
