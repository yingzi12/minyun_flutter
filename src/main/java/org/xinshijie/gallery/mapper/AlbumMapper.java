package org.xinshijie.gallery.mapper;

import org.apache.ibatis.annotations.Mapper;
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
public interface AlbumMapper  {
    List<Album> list(AlbumDto dto);
    Integer count(AlbumDto dto);

    Album getInfo(Long id);

    Integer updateError(Long id);

    Integer updateCountSee(Long id);

}




