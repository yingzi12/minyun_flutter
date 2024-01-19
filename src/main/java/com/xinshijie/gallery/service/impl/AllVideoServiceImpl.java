package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.enmus.VideoStatuEnum;
import com.xinshijie.gallery.mapper.AllVideoMapper;
import com.xinshijie.gallery.service.IAllVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllVideoServiceImpl extends ServiceImpl<AllVideoMapper, AllVideo> implements IAllVideoService {

    @Autowired
    private AllVideoMapper mapper;

    @Override
    public AllVideo getMD5(String md5) {
        QueryWrapper<AllVideo> qw = new QueryWrapper<>();
        qw.eq("md5", md5);
        AllVideo value = mapper.selectOne(qw);
        return value;
    }

    @Override
    public List<AllVideo> getListWait() {
        QueryWrapper<AllVideo> qw = new QueryWrapper<>();
        qw.eq("status", VideoStatuEnum.NORMAL);
        List<AllVideo> value = mapper.selectList(qw);
        return value;
    }
}
