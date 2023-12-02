package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.AlbumCollection;
import com.xinshijie.gallery.dto.AlbumCollectionDto;
import com.xinshijie.gallery.vo.AlbumCollectionVo;

import java.util.List;

/**
 * <p>
 * 用户收藏的album 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IAlbumCollectionService extends IService<AlbumCollection> {


    /**
     * 查询信息表
     */
    List<AlbumCollectionVo> selectAlbumCollectionList(AlbumCollectionDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<AlbumCollectionVo> selectPageAlbumCollection(AlbumCollectionDto dto);

    /**
     * 分页查询信息表
     */
    Page<AlbumCollectionVo> getPageAlbumCollection(AlbumCollectionDto dto);

    /**
     * 新增数据
     */
    AlbumCollection add(AlbumCollectionDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(AlbumCollectionDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    AlbumCollectionVo getInfo(Long id);
}
