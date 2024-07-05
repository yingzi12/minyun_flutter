package com.xinshijie.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.user.domain.UserChatContent;
import com.xinshijie.user.mapper.UserChatContentMapper;
import com.xinshijie.user.service.IUserChatContentService;
import com.xinshijie.user.dto.UserChatContentDto;
import com.xinshijie.user.vo.UserChatContentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户聊天记录  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserChatContentServiceImpl extends ServiceImpl<UserChatContentMapper, UserChatContent> implements IUserChatContentService {

    @Autowired
    private UserChatContentMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserChatContentVo> selectUserChatContentList(UserChatContentDto dto) {
        return mapper.selectListUserChatContent(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserChatContentVo> selectPageUserChatContent(UserChatContentDto dto) {
        Page<UserChatContentVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        return mapper.selectPageUserChatContent(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserChatContentVo> getPageUserChatContent(UserChatContentDto dto) {
        Page<UserChatContentVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        QueryWrapper<UserChatContentVo> qw = new QueryWrapper<>();
        return mapper.getPageUserChatContent(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserChatContent add(UserChatContentDto dto) {
        UserChatContent value = new UserChatContent();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserChatContentDto dto) {
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
    public UserChatContentVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
