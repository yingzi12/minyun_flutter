package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.AlbumVip;
import com.xinshijie.gallery.dto.AlbumVipDto;
import com.xinshijie.gallery.vo.AlbumVipVo;

import java.util.List;

/**
 * <p>
 * 用户vip 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IAlbumVipService extends IService<AlbumVip> {


    /**
     * 查询信息表
     */
    List<AlbumVipVo> selectAlbumVipList(AlbumVipDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<AlbumVipVo> selectPageAlbumVip(AlbumVipDto dto);

    /**
     * 分页查询信息表
     */
    Page<AlbumVipVo> getPageAlbumVip(AlbumVipDto dto);

    /**
     * 新增数据
     */
    AlbumVip add(AlbumVipDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(AlbumVipDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    AlbumVipVo getInfo(Long id);
}
