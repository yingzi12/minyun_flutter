package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.AllVideo;

public interface IAllVideoService extends IService<AllVideo> {
    AllVideo getMD5(String md5);
}
