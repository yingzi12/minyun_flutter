package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.dto.UserAttentionDto;
import com.xinshijie.gallery.service.IUserAttentionService;
import com.xinshijie.gallery.vo.UserAttentionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户关注的用户 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserAttentionController", description = "后台- 用户关注的用户")
@RestController
@RequestMapping("/UserAttention")
public class UserAttentionController extends BaseController {

    @Autowired
    private IUserAttentionService userAttentionService;

    /**
     * 世界年表 添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserAttention> add(@RequestBody UserAttentionDto dto) {
        UserAttention vo = userAttentionService.add(dto);
        return Result.success(vo);
    }

    /**
     * 世界年表 删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userAttentionService.delById(id);
        return Result.success(vo);
    }


    /**
     * 世界年表 修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserAttentionDto dto) {
        Integer vo = userAttentionService.edit(dto);
        return Result.success(vo);
    }


    /**
     * 世界年表 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserAttentionVo> getInfo(@PathVariable("id") Long id) {
        UserAttentionVo vo = userAttentionService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 世界年表 查询
     *
     * @return
     */

    @PostMapping("/select")
    public Result<Page<UserAttentionVo>> select(@RequestBody UserAttentionDto findDto) {
        Page<UserAttentionVo> vo = userAttentionService.selectPageUserAttention(findDto);
        return Result.success(vo);
    }


}
