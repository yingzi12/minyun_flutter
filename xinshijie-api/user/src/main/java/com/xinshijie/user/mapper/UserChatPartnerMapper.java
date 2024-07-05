package com.xinshijie.user.mapper;

import com.xinshijie.user.dto.UserChatPartnerDto;
import com.xinshijie.user.vo.UserChatPartnerVo;
import com.xinshijie.user.domain.UserChatPartner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户聊天对象表 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */

public interface UserChatPartnerMapper extends BaseMapper<UserChatPartner> {

    /**
    * 查询讨论主题表
    */
    List<UserChatPartnerVo> selectListUserChatPartner(UserChatPartnerDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserChatPartnerVo> selectPageUserChatPartner(Page<UserChatPartnerVo> page, @Param("dto") UserChatPartnerDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserChatPartnerVo> getPageUserChatPartner(Page<UserChatPartnerVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserChatPartnerDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserChatPartnerVo getInfo(Long id);
}

