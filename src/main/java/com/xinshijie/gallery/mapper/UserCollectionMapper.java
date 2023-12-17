package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.UserCollectionDto;
import com.xinshijie.gallery.vo.UserCollectionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户收藏的album Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Mapper
public interface UserCollectionMapper extends BaseMapper<UserCollection> {

    /**
     * 查询讨论主题表
     */
    List<UserCollectionVo> selectListUserCollection(UserCollectionDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserCollectionVo> selectPageUserCollection(Page<UserCollectionVo> page, @Param("dto") UserCollectionDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserCollectionVo> getPageUserCollection(Page<UserCollectionVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserCollectionDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserCollectionVo getInfo(Long id);

    IPage<Album> listSystem(Page<Album> page,@Param("userId")Integer userId);

    IPage<UserAlbum> listUser(Page<UserAlbum> page,@Param("userId")Integer userId);

}

