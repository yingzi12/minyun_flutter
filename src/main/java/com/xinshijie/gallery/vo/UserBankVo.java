package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-12-06
 */
@Data
@Schema(name = "UserBankVo", description = " ")
public class UserBankVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String bankName;

    private String bankCard;

    private String bankUser;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
