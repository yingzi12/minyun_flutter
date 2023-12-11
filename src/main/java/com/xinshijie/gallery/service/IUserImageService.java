package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.vo.UserImageVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 用户上传的图片 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserImageService extends IService<UserImage> {


    /**
     * 查询信息表
     */
    List<UserImageVo> selectUserImageList(UserImageDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserImageVo> selectPageUserImage(UserImageDto dto);

    Long selectCount(UserImageDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserImageVo> getPageUserImage(UserImageDto dto);

    /**
     * 新增数据
     */
    UserImage add(UserImageDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserImage dto);

    /**
     * 删除数据
     */
    Integer delById(Integer userId, Long id);

    Integer updateIsFree(Integer userId, Long id, Integer isFree);


    /**
     * 根据id数据
     */
    UserImageVo getInfo(Long id);

    String saveUploadedFiles(Integer userId, Integer aid, Integer isFree, MultipartFile file);
}
