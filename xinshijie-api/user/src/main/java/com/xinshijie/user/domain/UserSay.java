package com.xinshijie.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户说说，
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wiki_user_say")
@Schema(description = " 用户说说，")
public class UserSay implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String userName;
    private String userNickname;
    private LocalDateTime createTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片列表
     */
    private String urls;
    private LocalDateTime updateTime;
    /**
     * 点赞
     */
    private Integer countLike;

    private String avatar;

}
