package com.xinshijie.gallery.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.vo.AlbumVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author User
 * @description 针对表【album】的数据库操作Service
 * @createDate 2023-10-27 11:26:58
 */
public interface IAlbumService extends IService<Album> {
    IPage<Album> list(AlbumDto dto);

    Integer count(AlbumDto dto);

    AlbumVo getInfo(Integer id);

    Album getInfoBytitle(String title);

    void add(Album album);

    void updateError(Integer id);

    List<Album> findRandomStories(Integer pageSize);

    Integer updateSourceUrl(Album dto);

    String saveUploadedFiles(MultipartFile file);

    /**
     * 根据id修改数据
     */
    Boolean edit(Album dto);

    /**
     * 删除数据
     */
    Integer delById( Integer id);

    Integer updateCountImage(Integer aid);
}
