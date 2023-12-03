package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserBuyVip;
import com.xinshijie.gallery.dto.UserBuyVipDto;
import com.xinshijie.gallery.vo.UserBuyVipVo;

import java.util.List;

/**
 * <p>
 * 用户购买记录 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserBuyVipService extends IService<UserBuyVip> {


    /**
     * 查询信息表
     */
    List<UserBuyVipVo> selectUserBuyVipList(UserBuyVipDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserBuyVipVo> selectPageUserBuyVip(UserBuyVipDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserBuyVipVo> getPageUserBuyVip(UserBuyVipDto dto);

    /**
     * 新增数据
     */
    UserBuyVip add(UserBuyVipDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserBuyVipDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserBuyVipVo getInfo(Long id);
}
