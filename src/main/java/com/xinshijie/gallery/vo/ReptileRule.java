package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-11-03
 */
@Data
//(name = "ReptileRuleVo", description = " ")
public class ReptileRule implements Serializable {

    private static final long serialVersionUID = 1L;
    public String findUrl;
    public Integer isLocal;
    public String local_path;
    private Integer id;
    private Integer isHead;
    private Integer isUpdate;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 上次运行时间
     */
    private LocalDateTime latestTime;
    /**
     * 规则名称
     */
    //(description = "规则名称 ")
    private String title;
    /**
     * 网站地址
     */
    //(description = "网站地址 ")
    private String sourceWeb;
    /**
     * 状态 1运行 0停止 3 等待
     */
    //(description = "状态 1运行 0停止 3 等待 ")
    private Integer status;
    /**
     * 种类 1序列号,2多页,3 单页
     */
    //(description = "种类 1序列号,2多页,3 单页 ")
    private Integer kind;
    /**
     * page里面的最外一层
     */
    //(description = "page里面的最外一层 ")
    private String storyPageRule;
    /**
     * 获取分组
     */
    //(description = "获取分组 ")
    private String storyPageGroupRule;
    /**
     * 解析href
     */
    //(description = "解析href ")
    private String storyPageHrefRule;
    private String storyPageImgRule;
    /**
     * 需要不需要加URL
     */
    //(description = "需要不需要加URL ")
    private String storyUrl;
    private String titleRule;
    private String authorRule;
    private String typeNameRule;
    private String latestChapterRule;
    private String imageUrlRule;
    private String descRule;
    private String chapterListRule;
    private String chapterGroupRule;
    private String chapterUrlRule;
    private String chapterTitleRule;
    private Integer chapterUrlType;
    private String statusRule;
    /**
     * 需要部需要加url
     */
    private Integer storyUrlType;
    private String chapterContentRule;
    private String replaceListRule;
    private String pageUrl;
    /**
     * 手机网页会分页 第一层
     */
    //(description = "手机网页会分页 ")
    private String chapterPageRule;
    /**
     * 手机网页会分页  url
     */
    //(description = "手机网页会分页 ")
    private String chapterPageUrlRule;
    /**
     * 手机网页会分页 ,分页内容区 第二层
     */
    //(description = "手机网页会分页 ")
    private String contentPageRule;
    /**
     * 替换规则
     */
    //(description = "替换规则 ")
    private String storyRulReplaceRule;
    /**
     * 替换规则
     */
    //(description = "替换规则 ")
    private String chapterUrlReplaceRule;
    private Integer imageUrlType;
    private String chapterUrl;
    private String host;
    /**
     * 替换规则
     */
    //(description = "替换规则 ")
    private String iamgeUrlReplaceRule;
    private String storyPageAuthorRule;
    private String storyPageNameRule;
    private String imgUrl;


}
