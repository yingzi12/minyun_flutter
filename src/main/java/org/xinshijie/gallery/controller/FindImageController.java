package org.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xinshijie.gallery.common.Result;
import org.xinshijie.gallery.dao.FindImage;
import org.xinshijie.gallery.service.FindImageService;

import java.time.LocalDate;
import java.util.List;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/ ")
public class FindImageController {
    @Autowired
    private FindImageService findImageService;

    @GetMapping("/list")
    public Result<List<FindImage>> list() {
        Long total = findImageService.count();
        List<FindImage> list = findImageService.list();
        return Result.success(list,total.intValue());
    }

    @PostMapping("/add")
    public Result<Boolean> add(@Valid @RequestBody  FindImage dto) {
        dto.setSubTime(LocalDate.now().toString());
        dto.setCountFind(0);
        // 创建一个查询条件包装器
        QueryWrapper<FindImage> queryWrapper = Wrappers.query();

        // 添加查询条件
        queryWrapper.eq("title", dto.getTitle());

        boolean ok=findImageService.exists(queryWrapper);
        if(!ok) {
            Boolean total = findImageService.save(dto);
        }
        return Result.success(ok);
    }

    @GetMapping("/addFind")
    public Result<Integer> addFind(@RequestParam("id") Long id) {
        Integer total = findImageService.updateCountFind(id);
        return Result.success(total);
    }
}
