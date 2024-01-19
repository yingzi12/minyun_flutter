package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.AllImage;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.mapper.UserImageMapper;
import com.xinshijie.gallery.service.IAllImageService;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserImageService;
import com.xinshijie.gallery.vo.UserImageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户上传的图片  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserImageServiceImpl extends ServiceImpl<UserImageMapper, UserImage> implements IUserImageService {

    @Autowired
    private UserImageMapper mapper;

    @Autowired
    private IFileService fileService;
    @Autowired
    private IAllImageService allImageService;
    @Autowired
    private IUserAlbumService userAlbumService;
    //    @Value("${image.path}")
    private final String headPath = "/user/album/";
    @Value("${image.sourceWeb}")
    private String sourceWeb;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserImageVo> selectUserImageList(UserImageDto dto) {
        return mapper.selectListUserImage(dto);
    }

    @Override
    public List<UserImage> selectAllAid(Integer aid, Integer isFree) {
        QueryWrapper<UserImage> qw = new QueryWrapper<>();
        qw.eq("aid", aid);
        if (isFree != null) {
            qw.eq("is_free", isFree);
        }
        return mapper.selectList(qw);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public IPage<UserImageVo> selectPageUserImage(UserImageDto dto) {
        Page<UserImage> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        IPage<UserImageVo> value = mapper.selectPageUserImage(page, dto);
        return value;
    }

    @Override
    public Long selectCount(Integer aid, Integer userId, Integer isFree) {
        QueryWrapper<UserImage> qw = new QueryWrapper<>();
        qw.eq("aid", aid);
        if (userId != null) {
            qw.eq("user_id", userId);
        }
        if (isFree != null) {
            qw.eq("is_free", isFree);
        }
        return mapper.selectCount(qw);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserImageVo> getPageUserImage(UserImageDto dto) {
        Page<UserImageVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        QueryWrapper<UserImageVo> qw = new QueryWrapper<>();
        return mapper.getPageUserImage(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserImage add(UserImageDto dto) {
        UserImage value = new UserImage();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());

        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserImage dto) {
        return mapper.updateById(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Integer userId, Long id, Integer aid) {
        QueryWrapper<UserImage> qw = new QueryWrapper<>();
        qw.eq("id", id);
        qw.eq("user_id", userId);
        qw.eq("aid", aid);

        int i= mapper.delete(qw);
        userAlbumService.updateCountImage(aid);
         return i;
    }

    @Override
    public Integer updateIsFree(Integer userId, Long id, Integer isFree) {
        QueryWrapper<UserImage> qw = new QueryWrapper<>();
        qw.eq("id", id);
        qw.eq("user_id", userId);
        UserImage userImage = new UserImage();
        userImage.setIsFree(isFree);
        return mapper.update(userImage, qw);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserImageVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

    @Override
    public String saveUploadedFiles(Integer userId, Integer aid, Integer isFree, MultipartFile file) {
        UserAlbum userAlbum = userAlbumService.getInfo(aid);
        if (userAlbum == null) {
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        try {
            String md5 = fileService.getMD5(file.getInputStream());
            AllImage allImage = allImageService.getMD5(md5);
            if (allImage != null) {
                QueryWrapper<UserImage> qw = new QueryWrapper<>();
                qw.eq("md5", md5);
                qw.eq("aid", aid);
                UserImage value = mapper.selectOne(qw);
                if (value != null) {
                    return value.getImgUrl();
                } else {
                    UserImage userImage = new UserImage();
                    userImage.setUserId(userId);
                    userImage.setCreateTime(LocalDateTime.now());
                    userImage.setAid(aid);
                    userImage.setImgUrl(allImage.getSourceUrl());
                    userImage.setMd5(md5);
                    userImage.setIsFree(isFree);
                    mapper.insert(userImage);
                    userAlbumService.updateCountImage(aid);
                    return userImage.getImgUrl();
                }
            } else {
                allImage = new AllImage();
                allImage.setMd5(md5);
                allImage.setSize(file.getSize());
                allImage.setTitle(userAlbum.getTitle());
                //保存图片到本地
                String imgUrl = saveImage(allImage, md5, file);
                if (StringUtils.isEmpty(imgUrl)) {
                    throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
                }
                UserImage userImage = new UserImage();
                userImage.setUserId(userId);
                userImage.setCreateTime(LocalDateTime.now());
                userImage.setAid(aid);
                userImage.setImgUrl(imgUrl);
                userImage.setMd5(md5);
                userImage.setIsFree(isFree);
                mapper.insert(userImage);
                userAlbumService.updateCountImage(aid);
                return userImage.getImgUrl();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
        }
    }

    public String saveImage(AllImage allImage, String md5, MultipartFile file) {
        String url = fileService.saveUploadedFilesWatermark(headPath, allImage.getTitle(), file);
        try {
            if (StringUtils.isNotEmpty(url)) {
                allImage.setSourceWeb(sourceWeb);
                allImage.setSourceUrl(url);
                allImageService.save(allImage);
            }
        } catch (Exception ex) {
            //保存出问题。要么是md5出现重复，要么就数据库异常。
            allImage = allImageService.getMD5(md5);
            return allImage.getSourceUrl();
        }
        return url;
    }

}
