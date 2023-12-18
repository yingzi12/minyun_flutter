package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.dto.FindUserAttentionDto;
import com.xinshijie.gallery.dto.UserAttentionDto;
import com.xinshijie.gallery.dto.UserAttentionDto;
import com.xinshijie.gallery.service.IUserAttentionService;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import com.xinshijie.gallery.vo.UserAttentionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
     * 添加
     *
     * @return
     */
    @GetMapping("/on")
    public Result<UserAttention> on(@RequestParam("attUserId") Integer attUserId, @RequestParam("attUserName") String attUserName) {
        UserAttentionDto dto = new UserAttentionDto();
        Integer userId=getUserId();
        if(userId==null){
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        dto.setUserId(userId);
        dto.setUserName(getUserName());
        dto.setAttUserId(attUserId);
        dto.setAttUserName(attUserName);
        UserAttention vo = userAttentionService.add(dto);
        return Result.success(vo);
    }

    /**
     * 添加
     *
     * @return
     */
    @GetMapping("/close")
    public Result<Integer> close(@RequestParam("aid") Integer aid) {
        Integer vo = userAttentionService.delById(getUserId(), aid);
        return Result.success(vo);
    }

    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Integer id) {
        Integer vo = userAttentionService.delById(getUserId(), id);
        return Result.success(vo);
    }

    /**
     * 获取
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<UserAttention> getInfo(@PathVariable("id") Integer id) {
        UserAttention vo = userAttentionService.getInfo(getUserId(), id);
        return Result.success(vo);
    }
    

    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<SystemUserIntroVo>> list(FindUserAttentionDto findDto) {
        findDto.setUserId(getUserId());
        Page<SystemUserIntroVo> vo = userAttentionService.selectPageUserAttention(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }


}
