package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.AllImage;

public interface IAllImageService extends IService<AllImage> {
    AllImage getMD5(String md5);
}
