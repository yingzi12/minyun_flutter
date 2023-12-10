package com.xinshijie.gallery;


import com.xinshijie.gallery.common.vedio.MediaUtil;

import java.io.File;

public class VodioTest {
    public static void main(String args[]) throws Exception {

        MediaUtil.splitMp4ToTsSegmentsWithIndex(new File("/Users/luhuang/Documents/1700641117058.mp4")
                , new File("./1700641117058"), new File("./1700641117058/1700641117058.m3u8"));
    }

}
