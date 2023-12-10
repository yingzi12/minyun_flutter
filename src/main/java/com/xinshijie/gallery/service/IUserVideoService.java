package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.UserVideoDto;
import com.xinshijie.gallery.vo.UserVideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
public interface IUserVideoService extends IService<UserVideo> {


    /**
     * 查询信息表
     */
    List<UserVideoVo> selectUserVideoList(UserVideoDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserVideoVo> selectPageUserVideo(UserVideoDto dto);

    AllVideo checkAllMd5(String md5);
    /**
     * 分页查询信息表
     */
    Page<UserVideoVo> getPageUserVideo(UserVideoDto dto);

    /**
     * 新增数据
     */
    UserVideo add(UserVideo id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserVideo dto);


    /**
     * 删除数据
     */
    Integer delById(Integer userId, Long id);

    Integer updateIsFree(Integer userId, Long id, Integer isFree);

    /**
     * 根据id数据
     */
    UserVideoVo getInfo(Long id);

    Long getCount(Integer aid,Integer isFree);

    String saveUploadedFiles(Integer userId, Integer aid, Integer isFree, MultipartFile file);

    String updateUploadedFiles(Integer userId, Integer aid, Integer isFree, Long size, String md5, String sourcePath,String fileName);
}
