package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.WorkOrder;
import com.xinshijie.gallery.dto.WorkOrderDto;
import com.xinshijie.gallery.vo.WorkOrderVo;
import java.util.List;

/**
 * <p>
 * 服务工单 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-02-01
 */
public interface IWorkOrderService extends IService<WorkOrder> {


    /**
  * 查询信息表
  */
    List<WorkOrderVo> selectWorkOrderList(WorkOrderDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<WorkOrderVo> selectPageWorkOrder(WorkOrderDto dto);

    /**
     * 分页查询信息表
     */
    Page<WorkOrderVo> getPageWorkOrder(WorkOrderDto dto);

    /**
     * 新增数据
     */
    WorkOrder add(WorkOrderDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(WorkOrderDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    WorkOrderVo getInfo(Long id);
}
