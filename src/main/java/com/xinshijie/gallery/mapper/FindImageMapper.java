package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinshijie.gallery.dao.FindImage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author User
 * @description 针对表【find_image】的数据库操作Mapper
 * @createDate 2023-10-27 16:38:38
 * @Entity org.xinshijie.gallery.FindImage
 */
@Mapper
public interface FindImageMapper extends BaseMapper<FindImage> {

    Integer updateCountFind(Long id);

}




