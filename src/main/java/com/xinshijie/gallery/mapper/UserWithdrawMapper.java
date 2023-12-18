package com.xinshijie.gallery.mapper;

import com.xinshijie.gallery.dto.UserWithdrawDto;
import com.xinshijie.gallery.vo.UserWithdrawVo;
import com.xinshijie.gallery.domain.UserWithdraw;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户提现记录 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-18
 */
@Mapper
public interface UserWithdrawMapper extends BaseMapper<UserWithdraw> {

}

