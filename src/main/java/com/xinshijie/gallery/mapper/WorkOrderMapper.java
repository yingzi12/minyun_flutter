package com.xinshijie.gallery.mapper;

import com.xinshijie.gallery.dto.WorkOrderDto;
import com.xinshijie.gallery.vo.WorkOrderVo;
import com.xinshijie.gallery.domain.WorkOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 服务工单 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2024-02-01
 */

public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    /**
    * 查询讨论主题表
    */
    List<WorkOrderVo> selectListWorkOrder(WorkOrderDto dto);

    /**
     * 普通方法
     * 分页查询讨论主题表
     */
    Page<WorkOrderVo> selectPageWorkOrder(Page<WorkOrderVo> page, @Param("dto") WorkOrderDto dto);

    /**
     * 分页查询讨论主题表
     * 基于 MyBatis-Plus 的写法，xml文件中的 ${ew.customSqlSegment} 会根据 Wrapper wrapper的传参自动生成wherer 条件。不推荐复杂where或者是多表联合查询
     */
    Page<WorkOrderVo> getPageWorkOrder(Page<WorkOrderVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

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

