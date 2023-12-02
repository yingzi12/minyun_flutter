package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.mapper.UserImageMapper;
import com.xinshijie.gallery.service.IUserImageService;
import com.xinshijie.gallery.vo.UserImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户上传的图片  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserImageServiceImpl extends ServiceImpl<UserImageMapper, UserImage> implements IUserImageService {

    @Autowired
    private UserImageMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserImageVo> selectUserImageList(UserImageDto dto) {
        return mapper.selectListUserImage(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserImageVo> selectPageUserImage(UserImageDto dto) {
        Page<UserImageVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserImage(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserImageVo> getPageUserImage(UserImageDto dto) {
        Page<UserImageVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<UserImageVo> qw = new QueryWrapper<>();
        return mapper.getPageUserImage(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserImage add(UserImageDto dto) {
        UserImage value = new UserImage();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserImageDto dto) {
        return mapper.edit(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Long id) {
        return mapper.delById(id);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserImageVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
