package com.xinshijie.gallery.vo;

import com.xinshijie.gallery.dao.Album;
import lombok.Data;

@Data
public class AlbumVo {
    private Long id;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String sourceWeb;

    /**
     *
     */
    private Long hash;

    /**
     *
     */
    private Long countSee;

    private String girl;

    private String tags;

    private String createTime;
    private String imgUrl;
    private String size;
    private String numberPhotos;
    private String numberVideo;
    private String intro;
    //1 yes 0 No
    private Integer countError;
    private String sourceUrl;

    private Album pre;
    private Album next;

    private Integer isCollection;

}
