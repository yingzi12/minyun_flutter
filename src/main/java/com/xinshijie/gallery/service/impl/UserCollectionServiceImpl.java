package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.dto.UserCollectionDto;
import com.xinshijie.gallery.mapper.UserCollectionMapper;
import com.xinshijie.gallery.service.IUserCollectionService;
import com.xinshijie.gallery.vo.UserCollectionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户收藏的album  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements IUserCollectionService {

    @Autowired
    private UserCollectionMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserCollectionVo> selectUserCollectionList(UserCollectionDto dto) {
        return mapper.selectListUserCollection(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserCollectionVo> selectPageUserCollection(UserCollectionDto dto) {
        Page<UserCollectionVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserCollection(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserCollectionVo> getPageUserCollection(UserCollectionDto dto) {
        Page<UserCollectionVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<UserCollectionVo> qw = new QueryWrapper<>();
        return mapper.getPageUserCollection(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserCollection add(UserCollectionDto dto) {
        UserCollection value = new UserCollection();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserCollectionDto dto) {
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
    public UserCollectionVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
