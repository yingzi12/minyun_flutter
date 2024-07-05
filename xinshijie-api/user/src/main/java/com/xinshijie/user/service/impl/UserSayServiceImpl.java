package com.xinshijie.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.base.utils.SecurityUtils;
import com.xinshijie.user.domain.UserSay;
import com.xinshijie.user.dto.UserSayDto;
import com.xinshijie.user.mapper.UserSayMapper;
import com.xinshijie.user.service.IUserSayService;
import com.xinshijie.user.vo.UserSayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户说说，  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserSayServiceImpl extends ServiceImpl<UserSayMapper, UserSay> implements IUserSayService {

    @Autowired
    private UserSayMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserSayVo> selectUserSayList(UserSayDto dto) {
        return mapper.selectListUserSay(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSayVo> selectPageUserSay(UserSayDto dto) {
        Page<UserSayVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        return mapper.selectPageUserSay(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSayVo> getPageUserSay(UserSayDto dto) {
        Page<UserSayVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        QueryWrapper<UserSayVo> qw = new QueryWrapper<>();
        return mapper.getPageUserSay(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserSay add(UserSayDto dto) {
        Long userid = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        String nickName = SecurityUtils.getNickName();
        dto.setUserId(userid);
        dto.setUserName(username);
        dto.setCreateTime(LocalDateTime.now());
        dto.setUserNickname(nickName);
        dto.setUpdateTime(LocalDateTime.now());
        dto.setCountLike(0);
        UserSay value = new UserSay();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserSayDto dto) {
//        Long userid = SecurityUtils.getUserId();
//        String username = SecurityUtils.getUsername();
        dto.setUpdateTime(LocalDateTime.now());
        dto.setContent(dto.getContent());
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
    public UserSayVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
