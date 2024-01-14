package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    List<Image> list(ImageDto dto);

    Integer count(ImageDto dto);

    Integer addBatch(@Param("list") List<Image> list);

    Integer updateSourceUrl(@Param("dto") Image dto);

    Integer delCfAid(@Param("aid") Long aid);
}
