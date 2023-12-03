package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserVip;
import com.xinshijie.gallery.dto.UserVipDto;
import com.xinshijie.gallery.vo.UserVipVo;

import java.util.List;

/**
 * <p>
 * 用户vip 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserVipService extends IService<UserVip> {


    /**
     * 查询信息表
     */
    List<UserVipVo> selectUserVipList(UserVipDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserVipVo> selectPageUserVip(UserVipDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserVipVo> getPageUserVip(UserVipDto dto);

    /**
     * 新增数据
     */
    UserVip add(UserVipDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserVipDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserVipVo getInfo(Long id);
}
