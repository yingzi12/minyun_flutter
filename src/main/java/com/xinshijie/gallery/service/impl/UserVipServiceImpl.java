package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserVip;
import com.xinshijie.gallery.dto.UserVipDto;
import com.xinshijie.gallery.mapper.UserVipMapper;
import com.xinshijie.gallery.service.IUserVipService;
import com.xinshijie.gallery.vo.UserVipVo;
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
public class UserVipServiceImpl extends ServiceImpl<UserVipMapper, UserVip> implements IUserVipService {

    @Autowired
    private UserVipMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserVipVo> selectUserVipList(UserVipDto dto) {
        return mapper.selectListUserVip(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVipVo> selectPageUserVip(UserVipDto dto) {
        Page<UserVipVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        return mapper.selectPageUserVip(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserVipVo> getPageUserVip(UserVipDto dto) {
        Page<UserVipVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        QueryWrapper<UserVipVo> qw = new QueryWrapper<>();
        return mapper.getPageUserVip(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserVip add(UserVipDto dto) {
        UserVip value = new UserVip();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserVipDto dto) {
        return mapper.edit(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Long id) {
        return mapper.delById(id);
    }

    @Override
    public UserVip getInfo(Integer userId, Integer vipUserId) {
        QueryWrapper<UserVip> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("vip_user_id", vipUserId);
        return mapper.selectOne(qw);
    }


}
