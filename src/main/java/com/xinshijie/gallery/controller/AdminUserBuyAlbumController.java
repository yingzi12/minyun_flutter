package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserBuyAlbum;
import com.xinshijie.gallery.dto.UserBuyAlbumDto;
import com.xinshijie.gallery.service.IUserBuyAlbumService;
import com.xinshijie.gallery.vo.UserBuyAlbumVo;
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
@Tag(name = " AdminUserBuyAlbumController", description = "后台- 用户购买记录")
@RestController
@RequestMapping("/admin/userBuyAlbum")
public class AdminUserBuyAlbumController extends BaseController {

    @Autowired
    private IUserBuyAlbumService userBuyAlbumService;

    /**
     *  添加
     *
     * @return
     */

    @PostMapping("/add")
    public Result<UserBuyAlbum> add(@RequestBody UserBuyAlbumDto dto) {
        UserBuyAlbum vo = userBuyAlbumService.add(dto);
        return Result.success(vo);
    }

    /**
     *  删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userBuyAlbumService.delById(id);
        return Result.success(vo);
    }


    /**
     *  修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserBuyAlbumDto dto) {
        Integer vo = userBuyAlbumService.edit(dto);
        return Result.success(vo);
    }


    /**
     *  查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserBuyAlbum> getInfo(@PathVariable("aid") Integer aid) {
        UserBuyAlbum vo = userBuyAlbumService.getInfo(getUserId(),aid);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */
    @PostMapping("/select")
    public Result<List<UserBuyAlbumVo>> select(@RequestBody UserBuyAlbumDto findDto) {
        Page<UserBuyAlbumVo> vo = userBuyAlbumService.selectPageUserBuyAlbum(findDto);
        return Result.success(vo.getRecords(),Integer.parseInt(vo.getTotal()+""));
    }

}
