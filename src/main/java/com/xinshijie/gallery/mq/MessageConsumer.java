package com.xinshijie.gallery.mq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinshijie.gallery.common.Constants;
import com.xinshijie.gallery.common.vedio.VideoMetaInfo;
import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.enmus.VedioStatuEnum;
import com.xinshijie.gallery.service.IAllVideoService;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserVideoService;
import com.xinshijie.gallery.util.MediaUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MessageConsumer {

    @Autowired
    private IUserAlbumService albumService;
    @Autowired
    private IUserVideoService userVideoService;

    @Autowired
    private IAllVideoService allVideoService;

    @Autowired
    private IFileService fileService;

//    private String videoHcPath;
    /**
     * 接受消息
     * @param message
     */
    @RabbitListener(queues = "com.gallery.upload.video")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
        String[] uploadArr=message.split(":");
        String aid = uploadArr[0];
        String md5 = uploadArr[1];
        AllVideo video = allVideoService.getMD5(md5);
        if(video==null){
            return;
        }
        update(video,md5,"", VedioStatuEnum.NORMAL.getCode());
        //获取视频信息
        VideoMetaInfo videoMetaInfo=MediaUtil.getVideoMetaInfo(new File(Constants.videoHcPath+video.getUrl()));
        if(videoMetaInfo==null){
            return;
        }
        video.setSize(videoMetaInfo.getSize());
        video.setDuration(video.getDuration());
        //转换视频为ts
        String url= fileService.chargeVideoThreadFile(Constants.videoHcPath, video.getTitle(), video.getUrl());
        update( video,md5,url,VedioStatuEnum.LOCK.getCode());
    }

    public  void update( AllVideo video,String md5,String url,Integer status){
        QueryWrapper<UserVideo> qw=new QueryWrapper<>();
        qw.eq("md5",md5);
        UserVideo userVideo=new UserVideo();
        userVideo.setUrl(url);
        userVideo.setStatus(status);
        userVideoService.update(userVideo,qw);

//        AllVideo allVideo=new AllVideo();
//        allVideo.setId(video.getId());
        video.setSourceUrl(url);
        video.setStatus(status);
        allVideoService.updateById(video);
    }
}
