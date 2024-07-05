package com.xinshijie.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 讨论回复表
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "回复表")
public class UserSayCommentAddDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "内容")
    private String comment;

    @Schema(description = "说说id")
    private Long usid;
}
