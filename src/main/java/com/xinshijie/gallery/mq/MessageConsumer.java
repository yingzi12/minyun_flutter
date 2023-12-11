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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Time;

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

    @Value("${image.path}")
    private String savePath;
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
        update(video,md5,"", "",VedioStatuEnum.NORMAL.getCode());
        //获取视频信息
        VideoMetaInfo videoMetaInfo=MediaUtil.getVideoMetaInfo(new File(Constants.videoHcPath+video.getUrl()));
        if(videoMetaInfo==null){
            return;
        }
        video.setSize(videoMetaInfo.getSize());
        video.setDuration(video.getDuration());

        //转换视频为ts
        String url= fileService.chargeVideoThreadFile(Constants.videoHcPath, video.getTitle(), video.getUrl());
        String imagUrl = "";

        //生成预览图
        if(video.getDuration()> 13000) {
             imagUrl = url.replaceAll("m3u8", "gif");
            MediaUtil.cutVideoFrame(new File(Constants.videoHcPath + video.getUrl()), new File(savePath + imagUrl));
//            jietu(Constants.videoHcPath + video.getUrl(),savePath + imagUrl,10,videoMetaInfo.getWidth(),videoMetaInfo.getHeight() );
        }else {
             imagUrl = url.replaceAll("m3u8", "jpg");
//            int height = videoMetaInfo.getHeight() / videoMetaInfo.getWidth(); // 根据宽度计算适合的高度，防止画面变形
            jietu(Constants.videoHcPath + video.getUrl(),savePath + imagUrl,1,videoMetaInfo.getWidth(),videoMetaInfo.getHeight() );
        }

        update( video,md5,url,imagUrl,VedioStatuEnum.LOCK.getCode());
    }

    public  void update( AllVideo video,String md5,String url,String imgUrl,Integer status){
        QueryWrapper<UserVideo> qw=new QueryWrapper<>();
        qw.eq("md5",md5);
        UserVideo userVideo=new UserVideo();
        userVideo.setUrl(url);
        userVideo.setImgUrl(imgUrl);

        userVideo.setStatus(status);
        userVideoService.update(userVideo,qw);

//        AllVideo allVideo=new AllVideo();
//        allVideo.setId(video.getId());
        video.setSourceUrl(url);
        video.setStatus(status);
        video.setImgUrl(imgUrl);

        allVideoService.updateById(video);
    }

    public  void jietu(String vedioUrl,String imaUrl,  int startTimeInSeconds ,int width,int height ){
        File videoFile = new File(vedioUrl); // 源视频文件路径
        String outputFolderPath = imaUrl; // 转换后的文件输出文件夹路径
//        int startTimeInSeconds = 1; // 开始截取视频帧的时间点（单位：s）
//        int width = 300; // 截取的视频帧图片的宽度（单位：px）
//        int height = 300; // 截取的视频帧图片的高度（单位：px）
        int timeLengthInSeconds = 1; // 截取的视频帧的时长（从time开始算，单位:s）
        boolean isContinuous = false; // false - 静态图，true - 动态图

        Time time = new Time(startTimeInSeconds);
        MediaUtil.cutVideoFrame(videoFile, outputFolderPath, time, width, height, timeLengthInSeconds, isContinuous);
    }

//    public void delFile(String strPath){
//        try {
//            Path path = Paths.get(Constants.videoHcPath + video.getUrl());
////            Files.delete(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
