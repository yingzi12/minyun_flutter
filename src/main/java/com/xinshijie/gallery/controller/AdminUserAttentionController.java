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

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;


/**
 * <p>
 * 用户关注的用户 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserAttentionController", description = "后台- 用户关注的用户")
@RestController
@RequestMapping("/admin/userAttention")
public class AdminUserAttentionController extends BaseController {

    @Autowired
    private IUserAttentionService userAttentionService;

    /**
     *  添加
     *
     * @return
     */
    /**
     *  添加
     *
     * @return
     */
    @GetMapping("/add")
    public Result<UserAttention> add(@PathVariable("userId") Long userId, @PathVariable("userName") String userName) {
        UserAttentionDto userAttention=new UserAttentionDto();
        userAttention.setUserId(getUserId()+0L);
        userAttention.setUserName(getUserName());
        userAttention.setAttUserId(userId);
        userAttention.setAttUserName(userName);
        UserAttention vo = userAttentionService.add(userAttention);
        return Result.success(vo);
    }
    /**
     *  删除
     *
     * @return
     */

    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        Integer vo = userAttentionService.delById(getUserId(),id);
        return Result.success(vo);
    }



    /**
     *  查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserAttention> getInfo(@PathVariable("id") Long id) {
        UserAttention vo = userAttentionService.getInfo(getUserId(),id);
        return Result.success(vo);
    }


    /**
     *  查询
     *
     * @return
     */

    @GetMapping("/list")
    public Result<List<UserAttentionVo>> select( UserAttentionDto findDto) {
        findDto.setUserId(getUserId()+0l);
        Page<UserAttentionVo> vo = userAttentionService.selectPageUserAttention(findDto);
        return Result.success(vo.getRecords(),Integer.parseInt(vo.getTotal()+""));
    }


}
