package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserWithdraw;
import com.xinshijie.gallery.mapper.UserWithdrawMapper;
import com.xinshijie.gallery.service.IUserWithdrawService;
import com.xinshijie.gallery.dto.UserWithdrawDto;
import com.xinshijie.gallery.vo.AlbumVipVo;
import com.xinshijie.gallery.vo.UserWithdrawVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户提现记录  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserWithdrawServiceImpl extends ServiceImpl<UserWithdrawMapper, UserWithdraw> implements IUserWithdrawService {

    @Autowired
    private UserWithdrawMapper mapper;


    /**
     * 分页查询图片信息表
     */
    @Override
    public IPage<UserWithdraw> getPageUserWithdraw(UserWithdrawDto dto) {
        Page<UserWithdraw> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        QueryWrapper<UserWithdraw> qw = new QueryWrapper<>();
        qw.eq("user_id",dto.getUserId());
        return mapper.selectPage(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserWithdraw add(UserWithdrawDto dto) {
        UserWithdraw value = new UserWithdraw();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserWithdraw dto) {
        return mapper.updateById(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Long id) {
        return mapper.deleteById(id);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserWithdraw getInfo(Long id) {
        return mapper.selectById(id);
    }

}
