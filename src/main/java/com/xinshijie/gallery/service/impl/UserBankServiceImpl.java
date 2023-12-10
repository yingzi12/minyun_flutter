package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserBank;
import com.xinshijie.gallery.dto.UserBankDto;
import com.xinshijie.gallery.mapper.UserBankMapper;
import com.xinshijie.gallery.service.IUserBankService;
import com.xinshijie.gallery.vo.UserBankVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements IUserBankService {

    @Autowired
    private UserBankMapper mapper;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserBankVo> selectUserBankList(UserBankDto dto) {
        return mapper.selectListUserBank(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserBankVo> selectPageUserBank(UserBankDto dto) {
        Page<UserBankVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum() - 1) * dto.getPageSize());
        return mapper.selectPageUserBank(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserBankVo> getPageUserBank(UserBankDto dto) {
        Page<UserBankVo> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(20L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent((dto.getPageNum() - 1) * dto.getPageSize());
        QueryWrapper<UserBankVo> qw = new QueryWrapper<>();
        return mapper.getPageUserBank(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserBank add(UserBankDto dto) {
        UserBank value = new UserBank();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserBankDto dto) {
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
    public UserBankVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
