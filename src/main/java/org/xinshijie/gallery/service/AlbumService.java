package org.xinshijie.gallery.service;


import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dto.AlbumDto;

import java.util.List;

/**
* @author User
* @description 针对表【album】的数据库操作Service
* @createDate 2023-10-27 11:26:58
*/
public interface AlbumService  {
    List<Album> list(AlbumDto dto);

    Integer count(AlbumDto dto);

    Album getInfo(Long id);
}
