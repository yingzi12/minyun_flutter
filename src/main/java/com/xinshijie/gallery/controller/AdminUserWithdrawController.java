package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserWithdraw;
import com.xinshijie.gallery.service.IUserWithdrawService;
import com.xinshijie.gallery.dto.UserWithdrawDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;


/**
 * <p>
 *  用户提现记录 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserWithdrawController", description = "后台- 用户提现记录")
@RestController
@RequestMapping("/admin/userWithdraw")
public class AdminUserWithdrawController extends BaseController {

    @Autowired
    private IUserWithdrawService userWithdrawService;

    /**
    * 世界年表 添加
    * @return
    */
    @PostMapping("/add")
    public Result<UserWithdraw> add(@RequestBody  UserWithdrawDto dto){
        dto.setUserId(getUserId());
        dto.setUserName(getUserName());

        UserWithdraw vo = userWithdrawService.add(dto);
        return Result.success(vo);
    }


    /**
    * 世界年表 修改
    * @return
    */
    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserWithdraw dto){
        Integer  vo = userWithdrawService.edit(dto);
        return Result.success(vo);
    }


    /**
    * 世界年表 查询详情
    * @return
    */
    @GetMapping(value = "/getInfo/{id}")
    public Result<UserWithdraw> getInfo(@PathVariable("id") Long id) {
        UserWithdraw vo = userWithdrawService.getInfo(id);
        return Result.success(vo);
    }


    /**
    * 世界年表 查询
    * @return
    */
    @GetMapping("/list")
    public Result<List<UserWithdraw>> list( UserWithdrawDto findDto){
        findDto.setUserId(getUserId());
        IPage<UserWithdraw> vo = userWithdrawService.getPageUserWithdraw(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }


}
