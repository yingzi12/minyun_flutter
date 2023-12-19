package com.xinshijie.gallery;


import com.xinshijie.gallery.common.vedio.MediaUtil;
import com.xinshijie.gallery.enmus.VipPriceEnum;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class VodioTest {
    public static void main(String args[]) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateString = "2023-12-18 15:30:45";
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        //  dateString = "2023-12-18 15:30:45"
        //LocalDateTime.now()    "2023-12-19 15:30:45"
        // dateTime.isAfter(LocalDateTime.now()) false

        if(dateTime.isAfter(LocalDateTime.now())){
            System.out.println(true);
        }else {
            System.out.println(false);

        }
//        MediaUtil.splitMp4ToTsSegmentsWithIndex(new File("/Users/luhuang/Documents/1700641117058.mp4")
//                , new File("./1700641117058"), new File("./1700641117058/1700641117058.m3u8"));
    }

}
