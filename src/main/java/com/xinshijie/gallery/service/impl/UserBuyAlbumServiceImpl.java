package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserBuyAlbum;
import com.xinshijie.gallery.domain.UserVip;
import com.xinshijie.gallery.dto.UserBuyAlbumDto;
import com.xinshijie.gallery.mapper.UserBuyAlbumMapper;
import com.xinshijie.gallery.service.IUserBuyAlbumService;
import com.xinshijie.gallery.vo.UserBuyAlbumVo;
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
public class UserBuyAlbumServiceImpl extends ServiceImpl<UserBuyAlbumMapper, UserBuyAlbum> implements IUserBuyAlbumService {

    @Autowired
    private UserBuyAlbumMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserBuyAlbumVo> selectUserBuyAlbumList(UserBuyAlbumDto dto) {
        return mapper.selectListUserBuyAlbum(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserBuyAlbumVo> selectPageUserBuyAlbum(UserBuyAlbumDto dto) {
        Page<UserBuyAlbumVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserBuyAlbum(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserBuyAlbumVo> getPageUserBuyAlbum(UserBuyAlbumDto dto) {
        Page<UserBuyAlbumVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<UserBuyAlbumVo> qw = new QueryWrapper<>();
        return mapper.getPageUserBuyAlbum(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserBuyAlbum add(UserBuyAlbumDto dto) {
        UserBuyAlbum value = new UserBuyAlbum();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());

        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserBuyAlbumDto dto) {
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
    public UserBuyAlbum getInfo(Integer userId, Long aid) {
        QueryWrapper<UserBuyAlbum> qw=new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("aid",aid);
        return mapper.selectOne(qw);
    }
}
