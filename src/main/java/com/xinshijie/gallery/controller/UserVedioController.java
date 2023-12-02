package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserVedio;
import com.xinshijie.gallery.dto.UserVedioDto;
import com.xinshijie.gallery.service.IUserVedioService;
import com.xinshijie.gallery.vo.UserVedioVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserVedioController", description = "后台- ")
@RestController
@RequestMapping("/UserVedio")
public class UserVedioController extends BaseController {

    @Autowired
    private IUserVedioService userVedioService;

    /**
     * 世界年表 添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserVedio> add(@RequestBody UserVedioDto dto) {
        UserVedio vo = userVedioService.add(dto);
        return Result.success(vo);
    }

    /**
     * 世界年表 删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userVedioService.delById(id);
        return Result.success(vo);
    }


    /**
     * 世界年表 修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserVedioDto dto) {
        Integer vo = userVedioService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 世界年表 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserVedioVo> getInfo(@PathVariable("id") Long id) {
        UserVedioVo vo = userVedioService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 世界年表 查询
     *
     * @return
     */
    @PostMapping("/select")
    public Result<Page<UserVedioVo>> select(@RequestBody UserVedioDto findDto) {
        Page<UserVedioVo> vo = userVedioService.selectPageUserVedio(findDto);
        return Result.success(vo);
    }
}
