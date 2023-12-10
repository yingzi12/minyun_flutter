package com.xinshijie.gallery.mq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinshijie.gallery.common.Constants;
import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.enmus.VedioStatuEnum;
import com.xinshijie.gallery.service.IAllVideoService;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserVideoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        update(video.getId(),md5,"", VedioStatuEnum.NORMAL.getCode());
        String url= fileService.chargeVideoThreadFile(Constants.videoHcPath, video.getTitle(), video.getUrl());
        update(video.getId(),md5,url,VedioStatuEnum.LOCK.getCode());
    }

    public  void update(Long id,String md5,String url,Integer status){
        QueryWrapper<UserVideo> qw=new QueryWrapper<>();
        qw.eq("md5",md5);
        UserVideo userVideo=new UserVideo();
        userVideo.setUrl(url);
        userVideo.setStatus(status);
        userVideoService.update(userVideo,qw);

        AllVideo allVideo=new AllVideo();
        allVideo.setId(id);
        allVideo.setSourceUrl(url);
        allVideo.setStatus(status);
        allVideoService.updateById(allVideo);
    }
}
