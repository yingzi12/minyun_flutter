package com.xinshijie.gallery.mapper;

import com.xinshijie.gallery.dto.UserBankDto;
import com.xinshijie.gallery.vo.UserBankVo;
import com.xinshijie.gallery.domain.UserBank;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-06
 */

@Mapper
public interface UserBankMapper extends BaseMapper<UserBank> {

    /**
    * 查询讨论主题表
    */
    List<UserBankVo> selectListUserBank(UserBankDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserBankVo> selectPageUserBank(Page<UserBankVo> page, @Param("dto") UserBankDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserBankVo> getPageUserBank(Page<UserBankVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserBankDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserBankVo getInfo(Long id);
}

