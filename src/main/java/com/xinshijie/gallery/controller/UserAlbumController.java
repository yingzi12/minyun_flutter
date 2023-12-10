package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.vo.UserAlbumVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;


/**
 * <p>
 * 用户创建的 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserAlbumController", description = "后台- 用户创建的")
@RestController
@RequestMapping("/userAlbum")
public class UserAlbumController extends BaseController {

    @Autowired
    private IUserAlbumService userAlbumService;

    /**
     * 查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<UserAlbum> getInfo(@PathVariable("id") Long id) {
        UserAlbum vo = userAlbumService.getInfo(getUserId(), id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserAlbumVo>> select(UserAlbumDto findDto) {
        findDto.setStatus(AlbumStatuEnum.NORMAL.getCode());
        Page<UserAlbumVo> vo = userAlbumService.selectPageUserAlbum(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(vo.getTotal() + ""));
    }

}
