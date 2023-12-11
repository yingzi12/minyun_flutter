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
import org.springframework.beans.BeanUtils;
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
    public List<UserSettingVip> selectUserSettingVipList(UserSettingVipDto dto) {
        QueryWrapper<UserSettingVip> qw = new QueryWrapper<>();
        qw.eq("user_id", dto.getUserId());
        if(dto.getId()!=null) {
            qw.eq("id", dto.getId());
        }
        return mapper.selectList(qw);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSettingVipVo> selectPageUserSettingVip(UserSettingVipDto dto) {
        Page<UserSettingVipVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum() - 1) * dto.getPageSize());
        return mapper.selectPageUserSettingVip(page, dto);
    }

    @Override
    public Boolean updateStatus(Integer userId, Long id, Integer status) {
        QueryWrapper<UserSettingVip> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("id", id);

        UserSettingVip vip = new UserSettingVip();
        vip.setStatus(status);
        vip.setUpdateTime(LocalDateTime.now());
        int i = mapper.update(vip, qw);
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSettingVipVo> getPageUserSettingVip(UserSettingVipDto dto) {
        Page<UserSettingVipVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum() - 1) * dto.getPageSize());
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
        QueryWrapper<UserSettingVip> qw = new QueryWrapper<>();
        qw.eq("user_id", dto.getUserId());
        qw.eq("id", dto.getId());
        UserSettingVip settingVip = new UserSettingVip();
        BeanUtils.copyProperties(dto, settingVip);
        settingVip.setUpdateTime(LocalDateTime.now());
        return mapper.update(settingVip, qw);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Integer userId, Long id) {
        QueryWrapper<UserSettingVip> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("id", id);
        return mapper.delete(qw);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserSettingVip getInfo(Integer userId, Long id) {
        QueryWrapper<UserSettingVip> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("id", id);

        return mapper.selectOne(qw);
    }

}
