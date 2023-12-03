package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserVedio;
import com.xinshijie.gallery.dto.UserVedioDto;
import com.xinshijie.gallery.vo.UserVedioVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserVedioService extends IService<UserVedio> {


    /**
     * 查询信息表
     */
    List<UserVedioVo> selectUserVedioList(UserVedioDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserVedioVo> selectPageUserVedio(UserVedioDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserVedioVo> getPageUserVedio(UserVedioDto dto);

    /**
     * 新增数据
     */
    UserVedio add(UserVedioDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserVedioDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserVedioVo getInfo(Long id);
}
