package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserBuyVip;
import com.xinshijie.gallery.dto.UserBuyVipDto;
import com.xinshijie.gallery.mapper.UserBuyVipMapper;
import com.xinshijie.gallery.service.IUserBuyVipService;
import com.xinshijie.gallery.vo.UserBuyVipVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户购买记录  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserBuyVipServiceImpl extends ServiceImpl<UserBuyVipMapper, UserBuyVip> implements IUserBuyVipService {

    @Autowired
    private UserBuyVipMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserBuyVipVo> selectUserBuyVipList(UserBuyVipDto dto) {
        return mapper.selectListUserBuyVip(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserBuyVipVo> selectPageUserBuyVip(UserBuyVipDto dto) {
        Page<UserBuyVipVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        return mapper.selectPageUserBuyVip(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserBuyVipVo> getPageUserBuyVip(UserBuyVipDto dto) {
        Page<UserBuyVipVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        QueryWrapper<UserBuyVipVo> qw = new QueryWrapper<>();
        return mapper.getPageUserBuyVip(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserBuyVip add(UserBuyVipDto dto) {
        UserBuyVip value = new UserBuyVip();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());

        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserBuyVipDto dto) {
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
    public UserBuyVipVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
