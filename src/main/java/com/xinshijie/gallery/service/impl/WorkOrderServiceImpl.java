package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.WorkOrder;
import com.xinshijie.gallery.enmus.WorkStatuEnum;
import com.xinshijie.gallery.mapper.WorkOrderMapper;
import com.xinshijie.gallery.service.IWorkOrderService;
import com.xinshijie.gallery.dto.WorkOrderDto;
import com.xinshijie.gallery.vo.WorkOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务工单  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {

    @Autowired
    private WorkOrderMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<WorkOrderVo> selectWorkOrderList(WorkOrderDto dto) {
        return mapper.selectListWorkOrder(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<WorkOrderVo> selectPageWorkOrder(WorkOrderDto dto) {
        Page<WorkOrderVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        return mapper.selectPageWorkOrder(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<WorkOrderVo> getPageWorkOrder(WorkOrderDto dto) {
        Page<WorkOrderVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        QueryWrapper<WorkOrderVo> qw = new QueryWrapper<>();
        return mapper.getPageWorkOrder(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public WorkOrder add(WorkOrderDto dto) {
        WorkOrder value = new WorkOrder();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        value.setStatus(WorkStatuEnum.WAIT.getCode());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(WorkOrderDto dto) {
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
    public WorkOrderVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
