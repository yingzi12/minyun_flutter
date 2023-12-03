package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserVedio;
import com.xinshijie.gallery.dto.UserVedioDto;
import com.xinshijie.gallery.mapper.UserVedioMapper;
import com.xinshijie.gallery.service.IUserVedioService;
import com.xinshijie.gallery.vo.UserVedioVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserVedioServiceImpl extends ServiceImpl<UserVedioMapper, UserVedio> implements IUserVedioService {

    @Autowired
    private UserVedioMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserVedioVo> selectUserVedioList(UserVedioDto dto) {
        return mapper.selectListUserVedio(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVedioVo> selectPageUserVedio(UserVedioDto dto) {
        Page<UserVedioVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserVedio(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVedioVo> getPageUserVedio(UserVedioDto dto) {
        Page<UserVedioVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<UserVedioVo> qw = new QueryWrapper<>();
        return mapper.getPageUserVedio(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserVedio add(UserVedioDto dto) {
        UserVedio value = new UserVedio();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserVedioDto dto) {
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
    public UserVedioVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
