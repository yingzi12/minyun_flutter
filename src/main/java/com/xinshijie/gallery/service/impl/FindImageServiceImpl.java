package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.FindImage;
import com.xinshijie.gallery.mapper.FindImageMapper;
import com.xinshijie.gallery.service.FindImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @description 针对表【find_image】的数据库操作Service实现
 * @createDate 2023-10-27 16:38:38
 */
@Service
public class FindImageServiceImpl extends ServiceImpl<FindImageMapper, FindImage>
        implements FindImageService {
    @Autowired
    private FindImageMapper findImageMapper;


    @Override
    public Integer updateCountFind(Long id) {
        return findImageMapper.updateCountFind(id);
    }
}




