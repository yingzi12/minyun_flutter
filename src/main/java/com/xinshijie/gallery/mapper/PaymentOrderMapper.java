package com.xinshijie.gallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.vo.AlbumCollectionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-12-13
 */
@Mapper
public interface PaymentOrderMapper extends BaseMapper<PaymentOrder> {

   IPage<UserAlbum> listBuy(Page<UserAlbum> page, @Param("userId")Integer Integer, @Param("status")Integer status, @Param("kind")Integer kind);
}

