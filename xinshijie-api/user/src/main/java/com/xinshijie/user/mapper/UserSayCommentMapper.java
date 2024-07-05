package com.xinshijie.user.mapper;

import com.xinshijie.user.dto.UserSayCommentDto;
import com.xinshijie.user.vo.UserSayCommentVo;
import com.xinshijie.user.domain.UserSayComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 说说回复表 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */

public interface UserSayCommentMapper extends BaseMapper<UserSayComment> {

    /**
    * 查询讨论主题表
    */
    List<UserSayCommentVo> selectListUserSayComment(UserSayCommentDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserSayCommentVo> selectPageUserSayComment(Page<UserSayCommentVo> page, @Param("dto") UserSayCommentDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserSayCommentVo> getPageUserSayComment(Page<UserSayCommentVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserSayCommentDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserSayCommentVo getInfo(Long id);
}

