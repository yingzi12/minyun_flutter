package com.xinshijie.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.user.domain.UserChatPartner;
import com.xinshijie.user.mapper.UserChatPartnerMapper;
import com.xinshijie.user.service.IUserChatPartnerService;
import com.xinshijie.user.dto.UserChatPartnerDto;
import com.xinshijie.user.vo.UserChatPartnerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户聊天对象表  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserChatPartnerServiceImpl extends ServiceImpl<UserChatPartnerMapper, UserChatPartner> implements IUserChatPartnerService {

    @Autowired
    private UserChatPartnerMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserChatPartnerVo> selectUserChatPartnerList(UserChatPartnerDto dto) {
        return mapper.selectListUserChatPartner(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserChatPartnerVo> selectPageUserChatPartner(UserChatPartnerDto dto) {
        Page<UserChatPartnerVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        return mapper.selectPageUserChatPartner(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserChatPartnerVo> getPageUserChatPartner(UserChatPartnerDto dto) {
        Page<UserChatPartnerVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        QueryWrapper<UserChatPartnerVo> qw = new QueryWrapper<>();
        return mapper.getPageUserChatPartner(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserChatPartner add(UserChatPartnerDto dto) {
        UserChatPartner value = new UserChatPartner();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserChatPartnerDto dto) {
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
    public UserChatPartnerVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
