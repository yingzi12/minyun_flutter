package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.dto.UserAttentionDto;
import com.xinshijie.gallery.mapper.UserAttentionMapper;
import com.xinshijie.gallery.service.IUserAttentionService;
import com.xinshijie.gallery.vo.UserAttentionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户关注的用户  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserAttentionServiceImpl extends ServiceImpl<UserAttentionMapper, UserAttention> implements IUserAttentionService {

    @Autowired
    private UserAttentionMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserAttentionVo> selectUserAttentionList(UserAttentionDto dto) {
        return mapper.selectListUserAttention(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserAttentionVo> selectPageUserAttention(UserAttentionDto dto) {
        Page<UserAttentionVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum() - 1) * dto.getPageSize());
        return mapper.selectPageUserAttention(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserAttentionVo> getPageUserAttention(UserAttentionDto dto) {
        Page<UserAttentionVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum() - 1) * dto.getPageSize());
        QueryWrapper<UserAttentionVo> qw = new QueryWrapper<>();
        return mapper.getPageUserAttention(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserAttention add(UserAttentionDto dto) {
        UserAttention value = new UserAttention();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Integer userId, Long id) {
        QueryWrapper<UserAttention> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("id", id);

        return mapper.delete(queryWrapper);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserAttention getInfo(Integer userId, Long id) {
        QueryWrapper<UserAttention> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("id", id);

        return mapper.selectOne(queryWrapper);
    }

}
