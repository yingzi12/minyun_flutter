package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.dto.AlbumDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author User
 * @description 针对表【album】的数据库操作Mapper
 * @createDate 2023-10-27 11:26:58
 * @Entity com.xinshijie.gallery.Album
 */
@Mapper
public interface AlbumMapper extends BaseMapper<Album> {
    List<Album> list(AlbumDto dto);

    Integer count(AlbumDto dto);

    Integer add(@Param("dto") Album dto);

    Album getInfo(@Param("id") Integer id);

    Album getInfoByTitle(@Param("title") String title);

    Integer updateError(@Param("id") Integer id);

    Integer updateCountSee(@Param("id") Integer id, @Param("updateTime") String updateTime);

    Album previousChapter(@Param("id") Integer id);

    Album nextChapter(@Param("id") Integer id);

    List<Album> findRandomStories(@Param("randomId") Integer randomId, @Param("pageSize") Integer pageSize);

    Integer findMaxId();

    Integer findMinId();

    Integer updateSourceUrl(@Param("dto") Album dto);
}




