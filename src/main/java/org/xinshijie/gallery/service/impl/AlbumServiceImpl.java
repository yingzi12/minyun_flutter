package org.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.mapper.ImageMapper;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.mapper.AlbumMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author User
* @description 针对表【album】的数据库操作Service实现
* @createDate 2023-10-27 11:26:58
*/
@Service
public class AlbumServiceImpl implements AlbumService{
    @Autowired
    private AlbumMapper albumMapper;
    @Override
    public List<Album> list(AlbumDto dto) {
        return albumMapper.list(dto);
    }

    @Override
    public Integer count(AlbumDto dto) {
        return albumMapper.count(dto);
    }

    @Override
    public Album getInfo(Long id) {
        return albumMapper.getInfo(id);
    }
}




