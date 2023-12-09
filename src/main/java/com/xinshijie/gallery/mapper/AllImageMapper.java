package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.domain.AllImage;
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
public interface AllImageMapper extends BaseMapper<AllImage> {
}




