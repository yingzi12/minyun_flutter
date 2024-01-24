package com.xinshijie.gallery.vo;

import lombok.Data;

@Data
public class SystemInfoVo {
    //更新时间
    private String updateTime;
    //当前版本号
    private String newVersion;
    //版本号
    private Integer version;

    //上个版本号
    private String upVersion;
    //简介
    private String intro;
    //下载链接
    private String downUrl;


}
