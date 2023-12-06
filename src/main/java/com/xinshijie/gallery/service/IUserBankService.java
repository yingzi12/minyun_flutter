package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.UserBank;
import com.xinshijie.gallery.dto.UserBankDto;
import com.xinshijie.gallery.vo.UserBankVo;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-06
 */
public interface IUserBankService extends IService<UserBank> {


    /**
  * 查询信息表
  */
    List<UserBankVo> selectUserBankList(UserBankDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserBankVo> selectPageUserBank(UserBankDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserBankVo> getPageUserBank(UserBankDto dto);

    /**
     * 新增数据
     */
    UserBank add(UserBankDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserBankDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserBankVo getInfo(Long id);
}
