package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.domain.AlbumCollection;
import com.xinshijie.gallery.dto.AlbumCollectionDto;
import com.xinshijie.gallery.vo.AlbumCollectionVo;
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
public interface AlbumCollectionMapper extends BaseMapper<AlbumCollection> {

    /**
     * 查询讨论主题表
     */
    List<AlbumCollectionVo> selectListAlbumCollection(AlbumCollectionDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<AlbumCollectionVo> selectPageAlbumCollection(Page<AlbumCollectionVo> page, @Param("dto") AlbumCollectionDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<AlbumCollectionVo> getPageAlbumCollection(Page<AlbumCollectionVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

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

