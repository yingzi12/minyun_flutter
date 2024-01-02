package com.xinshijie.gallery.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(name = "UserBankDto", description = " ")
public class UserBankDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size(max = 100, message = " 超出最大长度 100")
    private String bankName;

    @Size(max = 100, message = " 超出最大长度 100")
    private String bankCard;

    @Size(max = 100, message = " 超出最大长度 100")
    private String bankUser;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long pageNum;

    private Long pageSize;
}
