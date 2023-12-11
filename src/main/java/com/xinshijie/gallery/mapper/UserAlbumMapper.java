package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.vo.UserAlbumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户创建的 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Mapper
public interface UserAlbumMapper extends BaseMapper<UserAlbum> {

    /**
     * 查询讨论主题表
     */
    List<UserAlbumVo> selectListUserAlbum(UserAlbumDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<UserAlbumVo> selectPageUserAlbum(Page<UserAlbumVo> page, @Param("dto") UserAlbumDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<UserAlbumVo> getPageUserAlbum(Page<UserAlbumVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id修改数据
     */
    Integer edit(UserAlbumDto dto);

    /**
     * 删除数据
     */
    Integer delById(Integer userId, Integer id);

    /**
     * 根据id数据
     */
    UserAlbumVo getInfo(Integer userId, Integer id);

    Integer updateCountImage(Integer id);

    Integer updateCountVideo(Integer id);

}

