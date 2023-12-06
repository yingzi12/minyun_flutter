package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

private Long pageNum;

private Long pageSize;
}
