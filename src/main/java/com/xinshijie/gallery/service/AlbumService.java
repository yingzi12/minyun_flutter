package com.xinshijie.gallery.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.vo.AlbumVo;

import java.util.List;

/**
 * @author User
 * @description 针对表【album】的数据库操作Service
 * @createDate 2023-10-27 11:26:58
 */
public interface AlbumService extends IService<Album> {
    List<Album> list(AlbumDto dto);

    Integer count(AlbumDto dto);

    AlbumVo getInfo(Long id);

    Album getInfoBytitle(String title);

    void add(Album album);

    void updateError(Long id);

    List<Album> findRandomStories(Integer pageSize);

    Integer updateSourceUrl(Album dto);
}
