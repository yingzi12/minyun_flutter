package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.*;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.UserVideoDto;
import com.xinshijie.gallery.mapper.UserVideoMapper;
import com.xinshijie.gallery.service.*;
import com.xinshijie.gallery.vo.UserVideoVo;
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
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserVideoServiceImpl extends ServiceImpl<UserVideoMapper, UserVideo> implements IUserVideoService {

    @Autowired
    private UserVideoMapper mapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IAllVideoService allVideoService;
    @Autowired
    private IUserAlbumService userAlbumService;
    //    @Value("${image.path}")
    private String headPath="/user/album/";
    @Value("${image.sourceWeb}")
    private String sourceWeb;
    /**
     * 查询图片信息表
     */
    @Override
    public List<UserVideoVo> selectUserVideoList(UserVideoDto dto) {
        return mapper.selectListUserVideo(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVideoVo> selectPageUserVideo(UserVideoDto dto) {
        Page<UserVideoVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        return mapper.selectPageUserVideo(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVideoVo> getPageUserVideo(UserVideoDto dto) {
        Page<UserVideoVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        QueryWrapper<UserVideoVo> qw = new QueryWrapper<>();
        return mapper.getPageUserVideo(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserVideo add(UserVideoDto dto) {
        UserVideo value = new UserVideo();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserVideo dto) {
        return mapper.updateById(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Integer userId,Long id) {
        QueryWrapper<UserVideo> qw=new QueryWrapper<>();
        qw.eq("id",id);
        qw.eq("userId",userId);
        return mapper.delete(qw);
    }

    @Override
    public Integer updateIsFree(Integer userId, Long id, Integer isFree) {
        QueryWrapper<UserVideo> qw=new QueryWrapper<>();
        qw.eq("id",id);
        qw.eq("userId",userId);
        UserVideo video=new UserVideo();
        video.setIsFree(isFree);
        return mapper.update(video,qw);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserVideoVo getInfo(Long id) {
        return mapper.getInfo(id);
    }


    @Override
    public String saveUploadedFiles(Integer userId, Integer aid, Integer isFree, MultipartFile file) {
        UserAlbum userAlbum=userAlbumService.getInfo(userId,aid+0L);
        if (userAlbum==null){
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        String url="";
        try {
            String md5=fileService.getMD5(file.getInputStream());

            AllVideo allVideo=allVideoService.getMD5(md5);
            if(allVideo !=null) {
                QueryWrapper<UserVideo> qw=new QueryWrapper<>();
                qw.eq("md5",md5);
                qw.eq("aid",aid);
                UserVideo value=mapper.selectOne(qw);
                if(value !=null) {
                    return value.getUrl();
                }else {
                    UserVideo userVideo=new UserVideo();
                    userVideo.setUserId(userId);
                    userVideo.setCreateTime(LocalDateTime.now());
                    userVideo.setAid(aid);
                    userVideo.setUrl(allVideo.getSource_url());
                    userVideo.setMd5(md5);
                    userVideo.setIsFree(isFree);
                    mapper.insert(userVideo);
                    userAlbumService.updateCountVideo(aid);
                    return userVideo.getUrl();
                }
            }else{
                allVideo = new AllVideo();
                allVideo.setMd5(md5);
                allVideo.setSize(file.getSize());
                allVideo.setTitle(userAlbum.getTitle());
                //保存图片到本地
                String imgUrl=saveFileVideo(allVideo,md5,file);
                if(StringUtils.isEmpty(imgUrl)){
                    throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
                }
                UserVideo userVideo=new UserVideo();
                userVideo.setUserId(userId);
                userVideo.setCreateTime(LocalDateTime.now());
                userVideo.setAid(aid);
                userVideo.setUrl(imgUrl);
                userVideo.setMd5(md5);
                userVideo.setIsFree(isFree);
                mapper.insert(userVideo);
                userAlbumService.updateCountVideo(aid);
                return userVideo.getUrl();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
        }
    }

    public String updateUploadedFiles(Integer userId, Integer aid, Integer isFree,Long size,String md5,String sourcePath) {
        UserAlbum userAlbum=userAlbumService.getInfo(userId,aid+0L);
        if (userAlbum==null){
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        String url="";
        try {
//            String md5=fileService.getMD5(file.getInputStream());

            AllVideo allVideo=allVideoService.getMD5(md5);
            if(allVideo !=null) {
                QueryWrapper<UserVideo> qw=new QueryWrapper<>();
                qw.eq("md5",md5);
                qw.eq("aid",aid);
                UserVideo value=mapper.selectOne(qw);
                if(value !=null) {
                    return value.getUrl();
                }else {
                    UserVideo userVideo=new UserVideo();
                    userVideo.setUserId(userId);
                    userVideo.setCreateTime(LocalDateTime.now());
                    userVideo.setAid(aid);
                    userVideo.setUrl(allVideo.getSource_url());
                    userVideo.setMd5(md5);
                    userVideo.setIsFree(isFree);
                    mapper.insert(userVideo);
                    userAlbumService.updateCountVideo(aid);
                    return userVideo.getUrl();
                }
            }else{
                allVideo = new AllVideo();
                allVideo.setMd5(md5);
                allVideo.setSize(size);
                allVideo.setTitle(userAlbum.getTitle());
                //保存图片到本地
                String imgUrl=saveUrlVideo(allVideo,md5,sourcePath);
                if(StringUtils.isEmpty(imgUrl)){
                    throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
                }
                UserVideo userVideo=new UserVideo();
                userVideo.setUserId(userId);
                userVideo.setCreateTime(LocalDateTime.now());
                userVideo.setAid(aid);
                userVideo.setUrl(imgUrl);
                userVideo.setMd5(md5);
                userVideo.setIsFree(isFree);
                mapper.insert(userVideo);
                userAlbumService.updateCountVideo(aid);
                return userVideo.getUrl();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
        }
    }

    public String saveFileVideo(AllVideo allVideo,String md5,MultipartFile file){
        String url=fileService.saveUploadedFilesWatermark(headPath,allVideo.getTitle(),file);
        try {
            allVideo.setSource_web(sourceWeb);
            allVideo.setSource_url(url);
            allVideoService.save(allVideo);
        }catch (Exception ex){
            //保存出问题。要么是md5出现重复，要么就数据库异常。
            allVideo=allVideoService.getMD5(md5);
            return allVideo.getSource_url();
        }
        return url;
    }

    public String saveUrlVideo(AllVideo allVideo,String md5,String sourcePath){
        String url=fileService.chargeVideoFile(headPath,allVideo.getTitle(),sourcePath);
        try {
            allVideo.setSource_web(sourceWeb);
            allVideo.setSource_url(url);
            allVideoService.save(allVideo);
        }catch (Exception ex){
            //保存出问题。要么是md5出现重复，要么就数据库异常。
            allVideo=allVideoService.getMD5(md5);
            return allVideo.getSource_url();
        }
        return url;
    }
}
