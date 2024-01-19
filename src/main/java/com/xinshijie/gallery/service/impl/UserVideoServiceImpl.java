package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.Constants;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.common.vedio.VideoMetaInfo;
import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.UserVideoDto;
import com.xinshijie.gallery.enmus.VideoStatuEnum;
import com.xinshijie.gallery.mapper.UserVideoMapper;
import com.xinshijie.gallery.mq.MessageProducer;
import com.xinshijie.gallery.service.IAllVideoService;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserVideoService;
import com.xinshijie.gallery.util.MediaUtil;
import com.xinshijie.gallery.vo.UserVideoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
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
//    private String headPath = "/user/album/";
    @Value("${image.sourceWeb}")
    private String sourceWeb;
    @Value("${image.path}")
    private String savePath;

    @Autowired
    private MessageProducer producer;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserVideo> selectUserVideoList(Integer aid, Integer userId, Integer isFree) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("aid", aid);
        if (userId != null) {
            qw.eq("user_id", userId);
        }
        if (isFree != null) {
            qw.eq("is_free", isFree);
        }
        return mapper.selectList(qw);
    }

    @Override
    public List<UserVideo> selectAllAid(Integer aid, Integer isFree) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
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
    public Page<UserVideoVo> selectPageUserVideo(UserVideoDto dto) {
        Page<UserVideoVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        return mapper.selectPageUserVideo(page, dto);
    }

    @Override
    public Long selectCount(Integer aid, Integer userId, Integer isFree) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("aid", aid);
        if (userId != null) {
            qw.eq("user_id", userId);
        }
        if (isFree != null) {
            qw.eq("is_free", isFree);
        }
        return mapper.selectCount(qw);
    }

    @Override
    public AllVideo checkAllMd5(String md5) {
        return allVideoService.getMD5(md5);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVideoVo> getPageUserVideo(UserVideoDto dto) {
        Page<UserVideoVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        QueryWrapper<UserVideoVo> qw = new QueryWrapper<>();
        return mapper.getPageUserVideo(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserVideo add(UserVideo dto) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("md5", dto.getMd5());
        qw.eq("aid", dto.getAid());
        UserVideo value = mapper.selectOne(qw);
        if (value != null) {
            throw new ServiceException(ResultCodeEnum.VEDIO_IS_EXICT);
        }
        value = new UserVideo();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        userAlbumService.updateCountVideo(dto.getAid());
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
    public Integer delById(Integer userId, Long id, Integer aid) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("id", id);
        qw.eq("user_id", userId);
        qw.eq("aid", aid);

        int i= mapper.delete(qw);
        userAlbumService.updateCountVideo(aid);
        return i;
    }

    @Override
    public Integer updateIsFree(Integer userId, Long id, Integer isFree) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("id", id);
        qw.eq("user_id", userId);
        UserVideo video = new UserVideo();
        video.setIsFree(isFree);
        return mapper.update(video, qw);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserVideoVo getInfo(Long id) {
        return mapper.getInfo(id);
    }


    public Long getCount(Integer aid, Integer isFree) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("aid", aid);
        if (isFree != null) {
            qw.eq("is_free", isFree);
        }
        return mapper.selectCount(qw);
    }

    @Override
    public String saveUploadedFiles(Integer userId, Integer aid, Integer isFree, MultipartFile file) {
        UserAlbum userAlbum = userAlbumService.getInfo(aid);
        if (userAlbum == null) {
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        String url = "";
        try {
            String md5 = fileService.getMD5(file.getInputStream());

            AllVideo allVideo = allVideoService.getMD5(md5);
            if (allVideo != null) {
                QueryWrapper<UserVideo> qw = new QueryWrapper<>();
                qw.eq("md5", md5);
                qw.eq("aid", aid);
                UserVideo value = mapper.selectOne(qw);
                if (value != null) {
                    return value.getUrl();
                } else {
                    UserVideo userVideo = new UserVideo();
                    userVideo.setUserId(userId);
                    userVideo.setCreateTime(LocalDateTime.now());
                    userVideo.setAid(aid);
                    userVideo.setUrl(allVideo.getSourceUrl());
                    userVideo.setMd5(md5);
                    userVideo.setIsFree(isFree);
                    mapper.insert(userVideo);
                    userAlbumService.updateCountVideo(aid);
                    return userVideo.getUrl();
                }
            } else {
                allVideo = new AllVideo();
                allVideo.setMd5(md5);
                allVideo.setSize(file.getSize());
                allVideo.setTitle(userAlbum.getTitle());
                //保存视频到本地
                String fileUrl = saveFileVideo(allVideo, md5, file);
                if (StringUtils.isEmpty(fileUrl)) {
                    throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
                }
                UserVideo userVideo = new UserVideo();
                userVideo.setUserId(userId);
                userVideo.setCreateTime(LocalDateTime.now());
                userVideo.setAid(aid);
                userVideo.setUrl(fileUrl);
                userVideo.setMd5(md5);
                userVideo.setIsFree(isFree);
                mapper.insert(userVideo);
                producer.sendMessage(aid, md5);
                userAlbumService.updateCountVideo(aid);
                return userVideo.getUrl();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
        }
    }

    public String updateUploadedFiles(Integer userId, Integer aid, Integer isFree, Long size, String md5, String sourcePath, String fileName) {
        Long count = this.getCount(aid, isFree);
        if (isFree == 1 && count > 3) {
            throw new ServiceException(ResultCodeEnum.VEDIO_UPLOAD_MAX);
        }
        if (isFree == 2 && count > 10) {
            throw new ServiceException(ResultCodeEnum.VEDIO_UPLOAD_MAX);
        }
        UserAlbum userAlbum = userAlbumService.getInfo(aid);
        if (userAlbum == null) {
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
//        String url = "";
        try {
            AllVideo allVideo = allVideoService.getMD5(md5);
            if (allVideo != null) {
                QueryWrapper<UserVideo> qw = new QueryWrapper<>();
                qw.eq("md5", md5);
                qw.eq("aid", aid);
                UserVideo value = mapper.selectOne(qw);
                if (value != null) {
                    return value.getUrl();
                } else {
                    UserVideo userVideo = new UserVideo();
                    userVideo.setUserId(userId);
                    userVideo.setCreateTime(LocalDateTime.now());
                    userVideo.setAid(aid);
                    userVideo.setUrl(allVideo.getSourceUrl());
                    userVideo.setMd5(md5);
                    userVideo.setStatus(allVideo.getStatus());
                    userVideo.setIsFree(isFree);
                    mapper.insert(userVideo);
                    userAlbumService.updateCountVideo(aid);
                    return userVideo.getUrl();
                }
            } else {
                allVideo = new AllVideo();
                allVideo.setMd5(md5);
                allVideo.setSize(size);
                allVideo.setStatus(VideoStatuEnum.WAIT.getCode());
                allVideo.setTitle(userAlbum.getTitle());
                allVideo.setUrl(sourcePath + "/" + fileName);
                allVideoService.save(allVideo);

                UserVideo userVideo = new UserVideo();
                userVideo.setUserId(userId);
                userVideo.setCreateTime(LocalDateTime.now());
                userVideo.setAid(aid);
                userVideo.setStatus(VideoStatuEnum.WAIT.getCode());
                userVideo.setUrl(sourcePath + "/" + fileName);
                userVideo.setMd5(md5);
                userVideo.setIsFree(isFree);
                mapper.insert(userVideo);

                producer.sendMessage(aid, md5);
                userAlbumService.updateCountVideo(aid);

                return userVideo.getUrl();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
        }
    }

    public String saveFileVideo(AllVideo allVideo, String md5, MultipartFile file) {
        //保存到本地
        String url = fileService.saveUploadedFilesDown(Constants.videoHcPath, allVideo.getTitle(), file);

        try {
            allVideo.setSourceWeb(sourceWeb);
            allVideo.setUrl(url);
            allVideoService.save(allVideo);
        } catch (Exception ex) {
            //保存出问题。要么是md5出现重复，要么就数据库异常。
            allVideo = allVideoService.getMD5(md5);
            return allVideo.getSourceUrl();
        }
        return url;
    }

//    public String saveUrlVideo(AllVideo allVideo, String md5, String sourcePath) {
//        String url = fileService.chargeVideoFile(headPath, allVideo.getTitle(), sourcePath);
//        try {
//            allVideo.setSourceWeb(sourceWeb);
//            allVideo.setSourceUrl(url);
//            allVideoService.save(allVideo);
//        } catch (Exception ex) {
//            //保存出问题。要么是md5出现重复，要么就数据库异常。
//            allVideo = allVideoService.getMD5(md5);
//            return allVideo.getSourceUrl();
//        }
//        return url;
//    }


    /**
     * 转码ts
     * @param video
     */
    public void transcodeTs(AllVideo video){
        updateVideo(video, video.getMd5(), "", "", VideoStatuEnum.NORMAL.getCode());
        File sourcefile= new File(Constants.videoHcPath + video.getUrl());
        //获取视频信息
        VideoMetaInfo videoMetaInfo = MediaUtil.getVideoMetaInfo(sourcefile);
        log.info("---------------------------获取视频信息成功-------------md5:{}--------------------------------",video.getMd5());
        if (videoMetaInfo == null) {
            return;
        }

        video.setSize(videoMetaInfo.getSize());
        video.setDuration(video.getDuration());
        String[] fileNameArr = video.getUrl().toString().split("\\.");
        String imagUrl = "/video/"+fileNameArr[0]+".jpg";
        //生成预览图
        if (video.getDuration() > 13000) {
//            imagUrl = video.getUrl().replaceAll(, "jpg");
            jietu(Constants.videoHcPath + video.getUrl(), savePath + imagUrl, 1, videoMetaInfo.getWidth(), videoMetaInfo.getHeight());
//            jietu(Constants.videoHcPath + video.getUrl(),savePath + imagUrl,10,videoMetaInfo.getWidth(),videoMetaInfo.getHeight() );
        } else {
//            imagUrl = video.getUrl().replaceAll("m3u8", "jpg");
//            int height = videoMetaInfo.getHeight() / videoMetaInfo.getWidth(); // 根据宽度计算适合的高度，防止画面变形
            jietu(Constants.videoHcPath + video.getUrl(), savePath + imagUrl, 1, videoMetaInfo.getWidth(), videoMetaInfo.getHeight());
        }
        log.info("---------------------------截图成功-------------md5:{}--------------------------------",video.getMd5());
        //转换视频为ts
        String url = fileService.chargeVideoThreadFile(Constants.videoHcPath, video.getTitle(), video.getUrl());
        log.info("---------------------------转码成功--------------md5:{}-------------------------------");

        updateVideo(video, video.getMd5(), url, imagUrl, VideoStatuEnum.LOCK.getCode());
//        delFile( video.getUrl());
    }
    public void updateVideo(AllVideo video, String md5, String url, String imgUrl, Integer status) {
        QueryWrapper<UserVideo> qw = new QueryWrapper<>();
        qw.eq("md5", md5);
        UserVideo userVideo = new UserVideo();
        userVideo.setUrl(url);
        userVideo.setImgUrl(imgUrl);

        userVideo.setStatus(status);
        this.update(userVideo, qw);

//        AllVideo allVideo=new AllVideo();
//        allVideo.setId(video.getId());
        video.setSourceUrl(url);
        video.setStatus(status);
        video.setImgUrl(imgUrl);

        allVideoService.updateById(video);
    }

    public void jietu(String vedioUrl, String imaUrl, int startTimeInSeconds, int width, int height) {
        File videoFile = new File(vedioUrl); // 源视频文件路径
        String outputFolderPath = imaUrl; // 转换后的文件输出文件夹路径
//        int startTimeInSeconds = 1; // 开始截取视频帧的时间点（单位：s）
//        int width = 300; // 截取的视频帧图片的宽度（单位：px）
//        int height = 300; // 截取的视频帧图片的高度（单位：px）
        int timeLengthInSeconds = 1; // 截取的视频帧的时长（从time开始算，单位:s）
        boolean isContinuous = false; // false - 静态图，true - 动态图

        Time time = new Time(startTimeInSeconds);
        File destinationFile = new File(imaUrl);
        File parentDir = destinationFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        MediaUtil.cutVideoFrame(videoFile, outputFolderPath, time, width, height, timeLengthInSeconds, isContinuous);
    }

    public void delFile(String strPath){
        try {
            Path path = Paths.get(Constants.videoHcPath + strPath);
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
