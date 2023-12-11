package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.AlbumVip;
import com.xinshijie.gallery.dto.AlbumVipDto;
import com.xinshijie.gallery.mapper.AlbumVipMapper;
import com.xinshijie.gallery.service.IAlbumVipService;
import com.xinshijie.gallery.vo.AlbumVipVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class AlbumVipServiceImpl extends ServiceImpl<AlbumVipMapper, AlbumVip> implements IAlbumVipService {

    @Autowired
    private AlbumVipMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<AlbumVipVo> selectAlbumVipList(AlbumVipDto dto) {
        return mapper.selectListAlbumVip(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<AlbumVipVo> selectPageAlbumVip(AlbumVipDto dto) {
        Page<AlbumVipVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        return mapper.selectPageAlbumVip(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<AlbumVipVo> getPageAlbumVip(AlbumVipDto dto) {
        Page<AlbumVipVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        QueryWrapper<AlbumVipVo> qw = new QueryWrapper<>();
        return mapper.getPageAlbumVip(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public AlbumVip add(AlbumVipDto dto) {
        AlbumVip value = new AlbumVip();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(AlbumVipDto dto) {
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
    public AlbumVipVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
