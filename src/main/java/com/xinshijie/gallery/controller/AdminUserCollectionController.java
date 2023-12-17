package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.UserCollectionDto;
import com.xinshijie.gallery.service.IUserCollectionService;
import com.xinshijie.gallery.vo.UserCollectionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;


/**
 * <p>
 * 用户收藏的album 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserCollectionController", description = "后台- 用户收藏的album")
@RestController
@RequestMapping("/admin/userCollection")
public class AdminUserCollectionController extends BaseController {

    @Autowired
    private IUserCollectionService userCollectionService;

    /**
     * 添加
     *
     * @return
     */
    @GetMapping("/on")
    public Result<UserCollection> on(@RequestParam("aid") Long aid, @RequestParam("title") String title, @RequestParam("ctype") Integer ctype) {
        UserCollectionDto dto = new UserCollectionDto();
        Integer userId=getUserId();
        if(userId==null){
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        dto.setUserId(userId);
        dto.setUserName(getUserName());
        dto.setAid(aid);
        dto.setTitle(title);
        dto.setCtype(ctype);
        UserCollection vo = userCollectionService.add(dto);
        return Result.success(vo);
    }

    /**
     * 添加
     *
     * @return
     */
    @GetMapping("/close")
    public Result<Integer> close(@RequestParam("aid") Long aid, @RequestParam("ctype") Integer ctype) {
        Integer vo = userCollectionService.delById(getUserId(), aid, ctype);
        return Result.success(vo);
    }

    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id, @RequestParam("ctype") Integer ctype) {
        Integer vo = userCollectionService.delById(getUserId(), id, ctype);
        return Result.success(vo);
    }

    /**
     * 获取
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<UserCollection> getInfo(@PathVariable("id") Integer id, @RequestParam("ctype") Integer ctype) {
        UserCollection vo = userCollectionService.getInfo(getUserId(), id, ctype);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */

    @GetMapping("/listSystem")
    public Result<List<Album>> listSystem(UserCollectionDto findDto) {
        findDto.setUserId(getUserId());
        IPage<Album> vo = userCollectionService.listSystem(findDto.getPageNum(), findDto.getPageSize(), findDto.getUserId());
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

    @GetMapping("/listUser")
    public Result<List<UserAlbum>> listUser(UserCollectionDto findDto) {
        findDto.setUserId(getUserId());
        IPage<UserAlbum> vo = userCollectionService.listUser(findDto.getPageNum(), findDto.getPageSize(), findDto.getUserId());
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

}
