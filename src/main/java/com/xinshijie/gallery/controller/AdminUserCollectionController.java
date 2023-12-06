package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.UserCollectionDto;
import com.xinshijie.gallery.service.IUserCollectionService;
import com.xinshijie.gallery.vo.UserCollectionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     *  添加
     *
     * @return
     */
    @GetMapping("/add")
    public Result<UserCollection> add(@RequestParam("aid") Long aid,@RequestParam("title") String title,@RequestParam("ctype") Integer ctype) {
        UserCollectionDto dto=new UserCollectionDto();
        dto.setUserId(getUserId()+0l);
        dto.setUserName(getUserName());
        dto.setAid(aid);
        dto.setTitle(title);
        dto.setCtype(ctype);
        UserCollection vo = userCollectionService.add(dto);
        return Result.success(vo);
    }

    /**
     *  删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id,@RequestParam("ctype") Integer ctype) {
        Integer vo = userCollectionService.delById(getUserId(),id,ctype);
        return Result.success(vo);
    }

    /**
     * 获取
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<UserCollection> getInfo(@PathVariable("id") Long id,@RequestParam("ctype") Integer ctype) {
        UserCollection vo = userCollectionService.getInfo(getUserId(),id,ctype);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */

    @PostMapping("/select")
    public Result<Page<UserCollectionVo>> select(@RequestBody UserCollectionDto findDto) {
        findDto.setUserId(getUserId()+0l);
        Page<UserCollectionVo> vo = userCollectionService.selectPageUserCollection(findDto);
        return Result.success(vo);
    }

}
