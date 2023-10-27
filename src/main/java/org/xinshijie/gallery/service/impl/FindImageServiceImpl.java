package org.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.xinshijie.gallery.dao.FindImage;
import org.xinshijie.gallery.service.FindImageService;
import org.xinshijie.gallery.mapper.FindImageMapper;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【find_image】的数据库操作Service实现
* @createDate 2023-10-27 16:38:38
*/
@Service
public class FindImageServiceImpl extends ServiceImpl<FindImageMapper, FindImage>
    implements FindImageService{
    @Autowired
    private FindImageMapper findImageMapper;


    @Override
    public Integer updateCountFind(Long id) {
        return findImageMapper.updateCountFind(id);
    }
}




