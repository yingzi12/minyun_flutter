package org.xinshijie.gallery.service;

import org.xinshijie.gallery.dao.FindImage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author User
* @description 针对表【find_image】的数据库操作Service
* @createDate 2023-10-27 16:38:38
*/
public interface FindImageService extends IService<FindImage> {

    Integer updateCountFind(Long id);
}
