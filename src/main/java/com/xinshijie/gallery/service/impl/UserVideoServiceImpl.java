package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.UserVideoDto;
import com.xinshijie.gallery.mapper.UserVideoMapper;
import com.xinshijie.gallery.service.IUserVideoService;
import com.xinshijie.gallery.vo.UserVideoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class UserVideoServiceImpl extends ServiceImpl<UserVideoMapper, UserVideo> implements IUserVideoService {

    @Autowired
    private UserVideoMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserVideoVo> selectUserVideoList(UserVideoDto dto) {
        return mapper.selectListUserVideo(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVideoVo> selectPageUserVideo(UserVideoDto dto) {
        Page<UserVideoVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        return mapper.selectPageUserVideo(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVideoVo> getPageUserVideo(UserVideoDto dto) {
        Page<UserVideoVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        QueryWrapper<UserVideoVo> qw = new QueryWrapper<>();
        return mapper.getPageUserVideo(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserVideo add(UserVideoDto dto) {
        UserVideo value = new UserVideo();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserVideo dto) {
        return mapper.updateById(dto);
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
    public UserVideoVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
