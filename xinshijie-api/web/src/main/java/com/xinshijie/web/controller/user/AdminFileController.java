/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.xinshijie.web.controller.user;


import com.xinshijie.base.vo.Result;
import com.xinshijie.user.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.xinshijie.base.utils.SecurityUtils.getUserId;


/**
 * 文件上传 controller
 * @author lgh
 *
 */
@RestController
@RequestMapping("/admin/file")
public class AdminFileController {
	
	@Autowired
	private IFileService fileService;


	@PutMapping("/uploadBatch")
	public Result<List<String>> handleFileBatchUpload(@RequestParam(value = "files") final List<MultipartFile> files) {
		List<String> value =fileService.uploadedBatchFiles("商城",getUserId(),files);
		return Result.success(value);
	}

	@PutMapping("/upload")
	public Result<String> handleFileUpload(@RequestParam(value = "file") final MultipartFile file) {
		String value =fileService.uploadedFiles("商城",getUserId(),file);
		return Result.success(value);
	}
	
}
