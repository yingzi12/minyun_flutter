package org.xinshijie.gallery.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName album
 */
@TableName(value ="album")
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
    private String page;

    /**
     * 
     */
    private String xh;

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
    private String imgUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}