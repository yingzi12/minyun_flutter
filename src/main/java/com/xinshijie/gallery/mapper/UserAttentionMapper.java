package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.dto.FindUserAttentionDto;
import com.xinshijie.gallery.dto.UserAttentionDto;
import com.xinshijie.gallery.vo.AlbumDiscoverVo;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import com.xinshijie.gallery.vo.UserAttentionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户关注的用户 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Mapper
public interface UserAttentionMapper extends BaseMapper<UserAttention> {

    /**
     * 查询讨论主题表
     */
    List<UserAttentionVo> selectListUserAttention(UserAttentionDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<SystemUserIntroVo> selectPageUserAttention(Page<SystemUserVo> page, @Param("userId") Integer userId);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserAttentionVo> getPageUserAttention(Page<UserAttentionVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserAttentionDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserAttentionVo getInfo(Long id);

    Page<AlbumDiscoverVo> discover(Page<UserAttention> page,@Param("userId") Integer userId);

}

