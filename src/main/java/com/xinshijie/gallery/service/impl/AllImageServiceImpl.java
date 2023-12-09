package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.AlbumVip;
import com.xinshijie.gallery.domain.AllImage;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.mapper.AlbumVipMapper;
import com.xinshijie.gallery.mapper.AllImageMapper;
import com.xinshijie.gallery.service.IAllImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllImageServiceImpl extends ServiceImpl<AllImageMapper, AllImage>  implements IAllImageService {

    @Autowired
    private  AllImageMapper mapper;
    @Override
    public AllImage getMD5(String md5) {
        QueryWrapper<AllImage> qw=new QueryWrapper<>();
        qw.eq("md5",md5);
        AllImage value=mapper.selectOne(qw);
        return value;
    }
}
