package org.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.mapper.ImageMapper;
import org.xinshijie.gallery.service.ImageService;

import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>   implements ImageService {
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
        QueryWrapper<Image> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        return imageMapper.delete(queryWrapper);
    }

    @Override
    public Integer addBatch(List<Image> list) {
        return imageMapper.addBatch(list);
    }

    @Override
    public List<Image> listAll(Long aid) {
        QueryWrapper<Image> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        return imageMapper.selectList(queryWrapper);
    }


}
