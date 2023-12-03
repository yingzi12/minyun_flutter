package com.xinshijie.gallery.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName album
 */
@TableName(value = "album")
@Data
public class Album implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
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

    private String gril;

    private String tags;

    private String createTime;
    private String updateTime;

    private String imgUrl;
    private String size;
    private Integer numberPhotos;
    private String numberVideo;
    private String intro;
    //1 yes 0 No
    private Integer countError;
    private String sourceUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}