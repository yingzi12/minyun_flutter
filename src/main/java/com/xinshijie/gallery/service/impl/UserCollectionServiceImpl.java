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

import java.time.LocalDateTime;
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
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        return mapper.selectPageUserCollection(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserCollectionVo> getPageUserCollection(UserCollectionDto dto) {
        Page<UserCollectionVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
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
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }


    /**
     * 删除数据
     */
    @Override
    public Integer delById(Integer userId, Long id, Integer ctype) {
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("id", id);
        queryWrapper.eq("ctype", ctype);

        return mapper.delete(queryWrapper);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserCollection getInfo(Integer userId, Long id, Integer ctype) {
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("id", id);
        queryWrapper.eq("ctype", ctype);

        return mapper.selectOne(queryWrapper);
    }

}
