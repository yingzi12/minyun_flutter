package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.Album;
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
    IPage<UserAlbum> selectPageUserAlbum(UserAlbumDto dto);

    List<UserAlbum> findRandomStories(Integer pageSize);


    /**
     * 分页查询信息表
     */
    IPage<UserAlbum> getPageUserAlbum(UserAlbumDto dto);

    /**
     * 新增数据
     */
    UserAlbum add(UserAlbumDto id);

    /**
     * 根据id修改数据
     */
    Boolean edit(UserAlbumDto dto);

    /**
     * 删除数据
     */
    Integer delById(Integer userId, Integer id);

    /**
     * 根据id数据
     */
    UserAlbum getInfo(Integer id);

    Integer updateCountSee(Integer id, String updateDate);

    UserAlbum previousChapter(Integer id);

    UserAlbum nextChapter(Integer id);

    Double getAmount(Integer aid, Integer userId, Integer charge, Double price, Double vipPrice);

    Boolean isCheck(Integer aid, Integer userId);

    Boolean updateCharge(Integer userId, Long id, Integer charge, Double price, Double vipPrice);


    Boolean updateStatus(Integer userId, Long id, Integer status);

    String saveUploadedFiles(String day, MultipartFile file);

    Integer updateCountImage(Integer id);

    Integer updateCountVideo(Integer id);

    void isCheckOperate(Integer aid);
}
