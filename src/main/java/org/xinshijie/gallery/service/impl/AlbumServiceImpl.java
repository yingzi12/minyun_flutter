package org.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.mapper.ImageMapper;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.mapper.AlbumMapper;
import org.springframework.stereotype.Service;
import org.xinshijie.gallery.service.ImageService;
import org.xinshijie.gallery.vo.AlbumVo;

import java.time.LocalDate;
import java.util.List;

/**
* @author User
* @description 针对表【album】的数据库操作Service实现
* @createDate 2023-10-27 11:26:58
*/
@Service
public class AlbumServiceImpl  extends ServiceImpl<AlbumMapper, Album>   implements AlbumService{
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
    public AlbumVo getInfo(Long id) {
        AlbumVo albumVo=new AlbumVo();
        albumMapper.updateCountSee(id, LocalDate.now().toString());
        Album pre =albumMapper. previousChapter( id);
        Album next= albumMapper.nextChapter( id);
        Album album= albumMapper.getInfo(id);
        BeanUtils.copyProperties(album,albumVo);
        albumVo.setPre(pre);
        albumVo.setNext(next);
        return albumVo;
    }

    @Override
    public Album getInfoBytitle(String title) {
//        albumMapper.updateCountSee(id);
        return albumMapper.getInfoByTitle(title);
    }
    @Override
    public void add(Album album) {
         albumMapper.add(album);
    }

    @Override
    public void updateError(Long id) {
         albumMapper.updateError(id);
    }
}




