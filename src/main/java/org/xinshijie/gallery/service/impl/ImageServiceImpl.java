package org.xinshijie.gallery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.mapper.ImageMapper;
import org.xinshijie.gallery.service.ImageService;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
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
}
