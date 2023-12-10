package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserBank;
import com.xinshijie.gallery.dto.UserBankDto;
import com.xinshijie.gallery.service.IUserBankService;
import com.xinshijie.gallery.vo.UserBankVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserBankController", description = "后台- ")
@RestController
@RequestMapping("/admin/userBank")
public class AdminUserBankController extends BaseController {

    @Autowired
    private IUserBankService userBankService;

    /**
     * 世界年表 添加
     *
     * @return
     */
    @PostMapping("/add")
    public Result<UserBank> add(@RequestBody UserBankDto dto) {
        UserBank vo = userBankService.add(dto);
        return Result.success(vo);
    }

    /**
     * 世界年表 删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userBankService.delById(id);
        return Result.success(vo);
    }


    /**
     * 世界年表 修改
     *
     * @return
     */
    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserBankDto dto) {
        Integer vo = userBankService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 世界年表 查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<UserBankVo> getInfo(@PathVariable("id") Long id) {
        UserBankVo vo = userBankService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 世界年表 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserBankVo>> select(UserBankDto findDto) {
        Page<UserBankVo> vo = userBankService.selectPageUserBank(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(vo.getTotal() + ""));
    }


}
