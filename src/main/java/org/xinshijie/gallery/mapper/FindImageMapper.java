package org.xinshijie.gallery.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.xinshijie.gallery.dao.FindImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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




