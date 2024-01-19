package com.xinshijie.gallery.mq;

import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.service.IAllVideoService;
import com.xinshijie.gallery.service.IUserVideoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
//
    @Autowired
    private IUserVideoService userVideoService;
//
    @Autowired
    private IAllVideoService allVideoService;
//
//    @Autowired
//    private IFileService fileService;
//
//    @Value("${image.path}")
//    private String savePath;
//    private String videoHcPath;

    /**
     * 接受消息
     *
     * @param message
     */
    @RabbitListener(queues = "com.gallery.upload.video")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
        String[] uploadArr = message.split(":");
        String aid = uploadArr[0];
        String md5 = uploadArr[1];
        AllVideo video = allVideoService.getMD5(md5);
        if (video == null) {
            return;
        }
        userVideoService.transcodeTs(video);
    }
}
