package org.xinshijie.gallery.vo;

import lombok.Data;

@Data
public class ResuImageVo {
    private Integer code = 200;
    private Long id;
    private String url;
    private String fileName;
    private Boolean uploaded;
    private String newFileName;
    private String originalFilename;
}
