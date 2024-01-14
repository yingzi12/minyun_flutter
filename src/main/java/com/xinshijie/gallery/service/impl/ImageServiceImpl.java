package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.dto.ImageDto;
import com.xinshijie.gallery.mapper.ImageMapper;
import com.xinshijie.gallery.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<Image> list(ImageDto dto) {
        return imageMapper.list(dto);
    }

    @Override
    public Integer count(ImageDto dto) {
        return imageMapper.count(dto);
    }

    @Override
    public Integer delAlum(Long aid) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid", aid);
        return imageMapper.delete(queryWrapper);
    }

    @Override
    public Integer addBatch(List<Image> list) {
        return imageMapper.addBatch(list);
    }

    @Override
    public List<Image> listAll(Long aid) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid", aid);
        return imageMapper.selectList(queryWrapper);
    }

    @Override
    public Integer updateSourceUrl(Image dto) {
        return imageMapper.updateSourceUrl(dto);
    }

    @Override
    public Integer delCfAid(Long aid) {
        return imageMapper.delCfAid(aid);
    }


}
