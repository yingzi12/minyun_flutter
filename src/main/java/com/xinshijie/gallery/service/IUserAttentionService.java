package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.dto.FindUserAttentionDto;
import com.xinshijie.gallery.dto.UserAttentionDto;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import com.xinshijie.gallery.vo.UserAttentionVo;

import java.util.List;

/**
 * <p>
 * 用户关注的用户 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserAttentionService extends IService<UserAttention> {


    /**
     * 查询信息表
     */
    List<UserAttentionVo> selectUserAttentionList(UserAttentionDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<SystemUserIntroVo> selectPageUserAttention(FindUserAttentionDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserAttentionVo> getPageUserAttention(UserAttentionDto dto);

    /**
     * 新增数据
     */
    UserAttention add(UserAttentionDto id);

    /**
     * 删除数据
     */
    Integer delById(Integer userId, Integer id);

    /**
     * 根据id数据
     */
    UserAttention getInfo(Integer userId, Integer id);

    UserAttention getInfoByAtten(Integer userId, Integer attUserId);

}
