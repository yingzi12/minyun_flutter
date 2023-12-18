package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.dto.FindSystemUserDto;
import com.xinshijie.gallery.dto.SystemUserDto;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-06
 */

@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 查询讨论主题表
     */
    List<SystemUserVo> selectListSystemUser(SystemUserDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<SystemUserIntroVo> selectPageSystemUser(Page<SystemUserVo> page, @Param("dto") FindSystemUserDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<SystemUserVo> getPageSystemUser(Page<SystemUserVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 删除数据
     */
    Integer delById(Integer id);

    /**
     * 根据id数据
     */
    SystemUserVo getInfo(Integer id);

    Integer updateIncome(@Param("userId") Integer userId,@Param("amount") Double amount);

    Integer updateWithdraw(@Param("userId") Integer userId,@Param("withdraw") Double withdraw);

}

