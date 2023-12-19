package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.UserWithdraw;
import com.xinshijie.gallery.enmus.WithdrawStatuEnum;
import com.xinshijie.gallery.enmus.WithdrawTypeEnum;
import com.xinshijie.gallery.mapper.UserWithdrawMapper;
import com.xinshijie.gallery.service.IPaypalService;
import com.xinshijie.gallery.service.ISystemUserService;
import com.xinshijie.gallery.service.IUserWithdrawService;
import com.xinshijie.gallery.dto.UserWithdrawDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private IPaypalService paypalService;

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
        BeanUtils.copyProperties(dto, value);
        Double amountReceived= paypalService.getProduct(dto.getAmount() ,0.7);
        if(!amountReceived.equals(dto.getAmountReceived())){
            throw new ServiceException(ResultCodeEnum.WITHDRAW_ERROR);
        }
        Integer updateCount=systemUserService.updateWithdraw(dto.getUserId(), dto.getAmount());
        if(updateCount==1) {
            value.setStatus(WithdrawStatuEnum.WAIT.getCode());
            value.setWithdrawType(WithdrawTypeEnum.PAYPAY.getCode());
            value.setAmountReceived(amountReceived);
            value.setUserId(dto.getUserId());
            value.setUserName(dto.getUserName());

            mapper.insert(value);
        }else{
            throw new ServiceException(ResultCodeEnum.WITHDRAW_ERROR);
        }
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
