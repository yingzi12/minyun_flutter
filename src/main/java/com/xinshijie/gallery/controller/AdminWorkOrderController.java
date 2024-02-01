package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.BaseController;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.WorkOrder;
import com.xinshijie.gallery.service.IWorkOrderService;
import com.xinshijie.gallery.dto.WorkOrderDto;
import com.xinshijie.gallery.vo.WorkOrderVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;


/**
 * <p>
 *  服务工单 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " WorkOrderController", description = "后台- 服务工单")
@RestController
@RequestMapping("/admin/workOrder")
public class AdminWorkOrderController extends BaseController {

    @Autowired
    private IWorkOrderService workOrderService;

    /**
    * 世界年表 添加
    * @return
    */
    @PostMapping("/add")
    public Result<WorkOrder> add(@RequestBody  WorkOrderDto dto){
        dto.setUserId(getUserId());
        dto.setUserName(getUserName());
        WorkOrder vo = workOrderService.add(dto);
        return Result.success(vo);
    }


    /**
    * 世界年表 查询详情
    * @return
    */
    @GetMapping(value = "/getInfo/{id}")
    public Result<WorkOrderVo> getInfo(@PathVariable("id") Long id) {
        WorkOrderVo vo = workOrderService.getInfo(id);
        return Result.success(vo);
    }


    /**
    * 世界年表 查询
    * @return
    */
    @PostMapping("/select")
    public Result<Page<WorkOrderVo>> select(@RequestBody WorkOrderDto findDto){
        Page<WorkOrderVo> vo = workOrderService.selectPageWorkOrder(findDto);
        return Result.success(vo);
    }

    /**
     * 查询元素列表
     */
    @GetMapping("/list")
    public Result<List<WorkOrderVo>> listWorkOrder(WorkOrderDto findDto) {
        findDto.setUserId(getUserId());
        IPage<WorkOrderVo> vo = workOrderService.selectPageWorkOrder(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

}
