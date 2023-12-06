package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.UserCollectionDto;
import com.xinshijie.gallery.vo.UserCollectionVo;

import java.util.List;

/**
 * <p>
 * 用户收藏的album 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserCollectionService extends IService<UserCollection> {


    /**
     * 查询信息表
     */
    List<UserCollectionVo> selectUserCollectionList(UserCollectionDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserCollectionVo> selectPageUserCollection(UserCollectionDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserCollectionVo> getPageUserCollection(UserCollectionDto dto);

    /**
     * 新增数据
     */
    UserCollection add(UserCollectionDto id);


    /**
     * 删除数据
     */
    Integer delById(Integer userId,Long id,Integer ctype);

    /**
     * 根据id数据
     */
    UserCollection getInfo(Integer userId,Long id,Integer ctype);
}
