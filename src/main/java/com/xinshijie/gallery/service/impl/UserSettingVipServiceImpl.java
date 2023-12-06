package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserSettingVip;
import com.xinshijie.gallery.dto.UserSettingVipDto;
import com.xinshijie.gallery.mapper.UserSettingVipMapper;
import com.xinshijie.gallery.service.IUserSettingVipService;
import com.xinshijie.gallery.vo.UserSettingVipVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户vip  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserSettingVipServiceImpl extends ServiceImpl<UserSettingVipMapper, UserSettingVip> implements IUserSettingVipService {

    @Autowired
    private UserSettingVipMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserSettingVipVo> selectUserSettingVipList(UserSettingVipDto dto) {
        return mapper.selectListUserSettingVip(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSettingVipVo> selectPageUserSettingVip(UserSettingVipDto dto) {
        Page<UserSettingVipVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserSettingVip(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSettingVipVo> getPageUserSettingVip(UserSettingVipDto dto) {
        Page<UserSettingVipVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<UserSettingVipVo> qw = new QueryWrapper<>();
        return mapper.getPageUserSettingVip(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserSettingVip add(UserSettingVipDto dto) {
        UserSettingVip value = new UserSettingVip();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserSettingVipDto dto) {
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
    public UserSettingVipVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
