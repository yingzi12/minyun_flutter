package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.vo.UserAlbumVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 用户创建的 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserAlbumService extends IService<UserAlbum> {


    /**
     * 查询信息表
     */
    List<UserAlbumVo> selectUserAlbumList(UserAlbumDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserAlbumVo> selectPageUserAlbum(UserAlbumDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserAlbumVo> getPageUserAlbum(UserAlbumDto dto);

    /**
     * 新增数据
     */
    UserAlbum add(UserAlbumDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserAlbumDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserAlbumVo getInfo(Long id);

    Boolean saveUploadedFiles(Integer userId, MultipartFile file);

}
