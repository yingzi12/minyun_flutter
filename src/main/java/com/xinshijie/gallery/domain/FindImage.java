package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName find_image
 */
@TableName(value = "find_image")
@Data
public class FindImage implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     *
     */
    @NotNull
    @Size(max = 200, min = 1)
    private String title;
    /**
     *
     */
//    @Size(max = 200,min = 1)
    private String girl;
    /**
     *
     */
//    @Size(max = 200,min = 1)
    private String createTime;
    /**
     *
     */
    private String subTime;
    /**
     *
     */
    private Integer countFind;
}