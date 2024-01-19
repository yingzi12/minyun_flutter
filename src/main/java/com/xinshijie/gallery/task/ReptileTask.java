package com.xinshijie.gallery.task;

import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.service.IAllVideoService;
import com.xinshijie.gallery.service.IReptileImageService;
import com.xinshijie.gallery.service.IUserVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReptileTask {

    @Autowired
    private IReptileImageService reptileImageService;
    @Autowired
    private IAllVideoService allVideoService;
    @Autowired
    private IUserVideoService userVideoService;


    //    @Scheduled(cron = "0 0 3 * * ?")
    public void tongbu() {
        log.info("同步图片");
        reptileImageService.singleDataThread();
//        System.out.println("Task 2 executed at: " + new Date());
    }


    /**
     * zhuanma
     */
    @Scheduled(cron = "0 1 * * * ?")
    public void zhuanma() {
        log.info("转码。。。。。。");
        List<AllVideo> videoList=allVideoService.getListWait();
        for(AllVideo video:videoList){
            userVideoService.transcodeTs(video);
        }
//        System.out.println("Task 2 executed at: " + new Date());
    }

//    @Scheduled(cron = "0 0 3 * * ?")
//    public void downLocad() {
//        log.info("同步图片");
//        reptileImageService.singleData();
////        System.out.println("Task 2 executed at: " + new Date());
//    }

}
