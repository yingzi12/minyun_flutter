package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserWithdraw;
import com.xinshijie.gallery.dto.UserWithdrawDto;
import com.xinshijie.gallery.vo.UserWithdrawVo;
import java.util.List;

/**
 * <p>
 * 用户提现记录 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-18
 */
public interface IUserWithdrawService extends IService<UserWithdraw> {


    /**
     * 分页查询信息表
     */
    IPage<UserWithdraw> getPageUserWithdraw(UserWithdrawDto dto);

    /**
     * 新增数据
     */
    UserWithdraw add(UserWithdrawDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserWithdraw dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserWithdraw getInfo(Long id);
}
