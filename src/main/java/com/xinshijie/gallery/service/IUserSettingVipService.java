package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserSettingVipDto;
import com.xinshijie.gallery.vo.UserSettingVipVo;

import java.util.List;

/**
 * <p>
 * 用户vip 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserSettingVipService extends IService<UserSettingVip> {


    /**
     * 查询信息表
     */
    List<UserSettingVipVo> selectUserSettingVipList(UserSettingVipDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserSettingVipVo> selectPageUserSettingVip(UserSettingVipDto dto);

    Boolean updateStatus(Integer userId, Long id, Integer status);


    /**
     * 分页查询信息表
     */
    Page<UserSettingVipVo> getPageUserSettingVip(UserSettingVipDto dto);

    /**
     * 新增数据
     */
    UserSettingVip add(UserSettingVipDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserSettingVipDto dto);

    /**
     * 删除数据
     */
    Integer delById(Integer userId, Long id);

    /**
     * 根据id数据
     */
    UserSettingVip getInfo(Integer userId, Long id);
}
