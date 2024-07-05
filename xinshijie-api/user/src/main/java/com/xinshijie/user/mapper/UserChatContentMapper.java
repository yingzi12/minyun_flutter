package com.xinshijie.user.mapper;

import com.xinshijie.user.dto.UserChatContentDto;
import com.xinshijie.user.vo.UserChatContentVo;
import com.xinshijie.user.domain.UserChatContent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户聊天记录 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */

public interface UserChatContentMapper extends BaseMapper<UserChatContent> {

    /**
    * 查询讨论主题表
    */
    List<UserChatContentVo> selectListUserChatContent(UserChatContentDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserChatContentVo> selectPageUserChatContent(Page<UserChatContentVo> page, @Param("dto") UserChatContentDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserChatContentVo> getPageUserChatContent(Page<UserChatContentVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserChatContentDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserChatContentVo getInfo(Long id);
}

