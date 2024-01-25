package com.xinshijie.gallery.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlbumDiscoverVo {
    private Integer userId;
    private Integer aid;
    private String nickname;
    private String userActor;
    private String title;
    private String intro;
    private LocalDateTime create_time;
    private String imgUrl;
    private Integer countSee;
    private Integer countLike;
    private Integer countCommon;

}
