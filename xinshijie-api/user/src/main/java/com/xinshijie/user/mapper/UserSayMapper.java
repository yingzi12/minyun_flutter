package com.xinshijie.user.mapper;

import com.xinshijie.user.dto.UserSayDto;
import com.xinshijie.user.vo.UserSayVo;
import com.xinshijie.user.domain.UserSay;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户说说， Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */

public interface UserSayMapper extends BaseMapper<UserSay> {

    /**
    * 查询讨论主题表
    */
    List<UserSayVo> selectListUserSay(UserSayDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserSayVo> selectPageUserSay(Page<UserSayVo> page, @Param("dto") UserSayDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserSayVo> getPageUserSay(Page<UserSayVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserSayDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserSayVo getInfo(Long id);
}

