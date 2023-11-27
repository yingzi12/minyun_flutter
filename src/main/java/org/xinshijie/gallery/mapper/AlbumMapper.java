package org.xinshijie.gallery.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xinshijie.gallery.dao.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;

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

    Album getInfo(@Param("id")Long id);

    Album getInfoByTitle(@Param("title")String title);


    Integer updateError(@Param("id")Long id);

    Integer updateCountSee(@Param("id") Long id,@Param("updateTime") String updateTime);

    Album previousChapter(@Param("id")Long id);

    Album nextChapter(@Param("id")Long id);

    List<Album> findRandomStories(@Param("randomId") Integer randomId,@Param("pageSize") Integer pageSize);

    Integer findMaxId();

    Integer findMinId();

    Integer updateSourceUrl(@Param("dto")Album dto);


}




