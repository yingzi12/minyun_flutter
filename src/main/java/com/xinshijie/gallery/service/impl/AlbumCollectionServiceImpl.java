package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.AlbumCollection;
import com.xinshijie.gallery.dto.AlbumCollectionDto;
import com.xinshijie.gallery.mapper.AlbumCollectionMapper;
import com.xinshijie.gallery.service.IAlbumCollectionService;
import com.xinshijie.gallery.vo.AlbumCollectionVo;
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
public class AlbumCollectionServiceImpl extends ServiceImpl<AlbumCollectionMapper, AlbumCollection> implements IAlbumCollectionService {

    @Autowired
    private AlbumCollectionMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<AlbumCollectionVo> selectAlbumCollectionList(AlbumCollectionDto dto) {
        return mapper.selectListAlbumCollection(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<AlbumCollectionVo> selectPageAlbumCollection(AlbumCollectionDto dto) {
        Page<AlbumCollectionVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        return mapper.selectPageAlbumCollection(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<AlbumCollectionVo> getPageAlbumCollection(AlbumCollectionDto dto) {
        Page<AlbumCollectionVo> page = new Page<>();
        if(dto.getPageNum()==null){
            dto.setPageNum(20L);
        }
        if(dto.getPageSize()==null){
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum()-1)* dto.getPageSize());
        QueryWrapper<AlbumCollectionVo> qw = new QueryWrapper<>();
        return mapper.getPageAlbumCollection(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public AlbumCollection add(AlbumCollectionDto dto) {
        AlbumCollection value = new AlbumCollection();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(AlbumCollectionDto dto) {
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
    public AlbumCollectionVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
