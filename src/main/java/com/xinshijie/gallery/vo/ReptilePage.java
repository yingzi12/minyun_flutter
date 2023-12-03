package com.xinshijie.gallery.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-11-03
 */
@Data
public class ReptilePage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String pageUrl;

    private Integer pageTotal;

    private Integer pageSize;


    private Integer nowPage;


    /**
     * 1 运行 0等待
     */
    private Integer status;

    private Integer ruleId;


}
