package com.xinshijie.gallery.task;

import com.xinshijie.gallery.service.IReptileImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReptileTask {

    @Autowired
    private IReptileImageService reptileImageService;

    // 每分钟执行一次
//    @Scheduled(cron = "0 0 3 * * ?")
    public void tongbu() {
        log.info("同步图片");
        reptileImageService.singleDataThread();
//        System.out.println("Task 2 executed at: " + new Date());
    }


//    @Scheduled(cron = "0 0 3 * * ?")
//    public void downLocad() {
//        log.info("同步图片");
//        reptileImageService.singleData();
////        System.out.println("Task 2 executed at: " + new Date());
//    }

}
