package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserBuyAlbum;
import com.xinshijie.gallery.dto.UserBuyAlbumDto;
import com.xinshijie.gallery.vo.UserBuyAlbumVo;

import java.util.List;

/**
 * <p>
 * 用户购买记录 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserBuyAlbumService extends IService<UserBuyAlbum> {


    /**
     * 查询信息表
     */
    List<UserBuyAlbumVo> selectUserBuyAlbumList(UserBuyAlbumDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserBuyAlbumVo> selectPageUserBuyAlbum(UserBuyAlbumDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserBuyAlbumVo> getPageUserBuyAlbum(UserBuyAlbumDto dto);

    /**
     * 新增数据
     */
    UserBuyAlbum add(UserBuyAlbumDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserBuyAlbumDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
     UserBuyAlbum getInfo(Integer userId, Integer aid);
}
