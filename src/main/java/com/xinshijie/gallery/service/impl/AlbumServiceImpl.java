package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.mapper.AlbumMapper;
import com.xinshijie.gallery.service.AlbumService;
import com.xinshijie.gallery.vo.AlbumVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author User
 * @description 针对表【album】的数据库操作Service实现
 * @createDate 2023-10-27 11:26:58
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {
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
    public AlbumVo getInfo(Integer id) {
        AlbumVo albumVo = new AlbumVo();
        albumMapper.updateCountSee(id, LocalDate.now().toString());
        Album pre = albumMapper.previousChapter(id);
        Album next = albumMapper.nextChapter(id);
        Album album = albumMapper.getInfo(id);
        if (album == null) {
            throw new ServiceException("图集不存在！");
        }
        BeanUtils.copyProperties(album, albumVo);
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
    public void updateError(Integer id) {
        albumMapper.updateError(id);
    }

    @Override
    public List<Album> findRandomStories(Integer pageSize) {
        Integer maxId = albumMapper.findMaxId(); //
        Integer minId = albumMapper.findMinId(); //
        Integer randomId = ThreadLocalRandom.current().nextInt(minId, maxId - 30);
        return albumMapper.findRandomStories(randomId, pageSize);
    }

    public Integer updateSourceUrl(Album dto) {
        return albumMapper.updateSourceUrl(dto);
    }
}




