ALTER TABLE album ADD is_vip int DEFAULT 2 NOT NULL;
ALTER TABLE album ADD is_free int DEFAULT 2 NOT NULL;
ALTER TABLE image ADD md5 varchar(100) NULL;

-- gallery.all_image definition

CREATE TABLE `all_image` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `source_web` varchar(100) DEFAULT NULL,
                             `source_url` varchar(100) DEFAULT NULL,
                             `md5` varchar(100) DEFAULT NULL,
                             `size` bigint DEFAULT NULL,
                             `title` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `all_image_UN` (`md5`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='所有的图片';


-- gallery.all_video definition

CREATE TABLE `all_video` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `source_web` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `source_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `md5` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `size` bigint DEFAULT NULL,
                             `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `status` int NOT NULL DEFAULT '1',
                             `url` varchar(100) DEFAULT NULL,
                             `duration` bigint NOT NULL DEFAULT '0',
                             `img_url` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `all_image_UN` (`md5`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='所有的图片';


-- gallery.`order` definition

CREATE TABLE `order` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `amount` double NOT NULL DEFAULT '0',
                         `pay_id` varchar(20) NOT NULL,
                         `name` varchar(100) NOT NULL,
                         `describe` varchar(100) DEFAULT NULL,
                         `create_time` datetime DEFAULT NULL,
                         `pay_time` datetime DEFAULT NULL,
                         `status` int NOT NULL DEFAULT '0',
                         `user_id` int DEFAULT NULL,
                         `username` varchar(100) DEFAULT NULL,
                         `country` varchar(30) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gallery.pay_event definition

CREATE TABLE `pay_event` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `event_body` text NOT NULL,
                             `enent_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `create_time` datetime NOT NULL,
                             `summary` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `resource` text,
                             `order_id` varchar(100) DEFAULT NULL,
                             `event_type` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gallery.payment_order definition

CREATE TABLE `payment_order` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `amount` double NOT NULL DEFAULT '0' COMMENT '金额',
                                 `pay_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
                                 `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物品名称',
                                 `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单说明',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
                                 `status` int NOT NULL DEFAULT '0' COMMENT '状态 1 等待支付，2支付完成 3退款 4取消',
                                 `user_id` int DEFAULT NULL COMMENT '用户id',
                                 `username` varchar(100) DEFAULT NULL,
                                 `country` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '货币',
                                 `kind` int NOT NULL DEFAULT '0' COMMENT '物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集',
                                 `product_id` int NOT NULL DEFAULT '0' COMMENT '物品类别',
                                 `pay_type` varchar(100) DEFAULT NULL COMMENT '支付方式',
                                 `expired_time` datetime DEFAULT NULL COMMENT '最晚支付时间',
                                 `request_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `income_user_id` int DEFAULT NULL,
                                 `paid_amount` double NOT NULL DEFAULT '0' COMMENT '实付金额',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `payment_order_UN` (`pay_id`),
                                 KEY `payment_order_kind_IDX` (`kind`,`product_id`) USING BTREE,
                                 KEY `payment_order_requst_id_IDX` (`request_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gallery.`system_user` definition

CREATE TABLE `system_user` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `name` varchar(100) DEFAULT NULL,
                               `nickname` varchar(100) DEFAULT NULL,
                               `email` varchar(100) DEFAULT NULL,
                               `is_email` int NOT NULL DEFAULT '2',
                               `password` varchar(100) DEFAULT NULL,
                               `salt` varchar(20) DEFAULT NULL,
                               `create_time` datetime DEFAULT NULL,
                               `update_time` datetime DEFAULT NULL,
                               `intro` varchar(100) DEFAULT NULL,
                               `directions` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                               `img_url` varchar(100) DEFAULT NULL,
                               `count_attention` int DEFAULT '0' COMMENT '关注',
                               `count_see` int DEFAULT '0' COMMENT '查看',
                               `count_like` int DEFAULT '0' COMMENT '喜欢',
                               `vip` int NOT NULL DEFAULT '0',
                               `credit` int NOT NULL DEFAULT '0' COMMENT '信用分',
                               `vip_expiration_time` datetime DEFAULT NULL COMMENT 'vip过期时间',
                               `income` double NOT NULL DEFAULT '0',
                               `withdraw` double NOT NULL DEFAULT '0' COMMENT '提现金额',
                               `amount_received` double NOT NULL DEFAULT '0' COMMENT '到账金额',
                               `invite` varchar(6) DEFAULT NULL COMMENT '邀请码',
                               `count_invite` int NOT NULL DEFAULT '0',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `system_user_UN` (`name`),
                               UNIQUE KEY `system_user_emial_UN` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gallery.user_album definition

CREATE TABLE `user_album` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `title` varchar(100) DEFAULT NULL COMMENT '标题',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `user_id` bigint NOT NULL COMMENT '用户id',
                              `user_name` varchar(100) NOT NULL COMMENT '用户名称',
                              `intro` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
                              `tags` varchar(100) DEFAULT NULL COMMENT '标签',
                              `is_vip` int DEFAULT '2' COMMENT '是否vip',
                              `charge` int DEFAULT '1' COMMENT '''1 免费'', ''2 VIP免费'', ''3 VIP折扣'', ''4 VIP独享'' 5.统一',
                              `price` double DEFAULT '0' COMMENT '价格',
                              `discount` double DEFAULT '100' COMMENT '折扣',
                              `girl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模特',
                              `count_see` int DEFAULT '0' COMMENT '查看数',
                              `number_photos` int NOT NULL DEFAULT '0' COMMENT '照片数',
                              `number_video` int NOT NULL DEFAULT '0' COMMENT '视频数',
                              `count_collection` int NOT NULL DEFAULT '0' COMMENT '收藏数',
                              `count_buy` int DEFAULT '0' COMMENT '购买数',
                              `score` double DEFAULT '0',
                              `introduce` varchar(500) DEFAULT NULL COMMENT '介绍',
                              `status` int DEFAULT '1' COMMENT '状态',
                              `img_url` varchar(100) DEFAULT NULL,
                              `count_like` int DEFAULT '0',
                              `md5` varchar(100) DEFAULT NULL,
                              `vip_price` double DEFAULT '0',
                              `pay_intro` text COMMENT '只有付费解锁了才能看到的内容',
                              `update_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户创建的';


-- gallery.user_attention definition

CREATE TABLE `user_attention` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `user_id` bigint DEFAULT NULL,
                                  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `att_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `att_user_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `user_attention_unique` (`user_id`,`att_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关注的用户';


-- gallery.user_bank definition

CREATE TABLE `user_bank` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `bank_name` varchar(100) DEFAULT NULL,
                             `bank_card` varchar(100) DEFAULT NULL,
                             `bank_user` varchar(100) DEFAULT NULL,
                             `create_time` datetime DEFAULT NULL,
                             `update_time` datetime DEFAULT NULL,
                             `user_id` int DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gallery.user_buy_album definition

CREATE TABLE `user_buy_album` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `user_id` bigint DEFAULT NULL,
                                  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `aid` bigint NOT NULL DEFAULT '0' COMMENT 'aid',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  `price` double NOT NULL DEFAULT '0' COMMENT '价格',
                                  `buy_time` datetime DEFAULT NULL COMMENT '购买时间',
                                  `status` int NOT NULL DEFAULT '0' COMMENT '状态',
                                  `title` varchar(100) DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户购买记录';


-- gallery.user_buy_vip definition

CREATE TABLE `user_buy_vip` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `user_id` bigint DEFAULT NULL,
                                `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `vid` bigint NOT NULL DEFAULT '0' COMMENT 'aid',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `price` double NOT NULL DEFAULT '0' COMMENT '价格',
                                `buy_time` datetime DEFAULT NULL COMMENT '购买时间',
                                `status` int NOT NULL DEFAULT '0' COMMENT '状态',
                                `title` varchar(100) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户购买记录';


-- gallery.user_collection definition

CREATE TABLE `user_collection` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `user_id` bigint DEFAULT NULL,
                                   `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                   `aid` bigint DEFAULT NULL,
                                   `ctype` int NOT NULL DEFAULT '1' COMMENT '1 系统 2 用户',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `user_collection_UN` (`user_id`,`aid`,`ctype`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏的album';


-- gallery.user_image definition

CREATE TABLE `user_image` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `img_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `create_time` datetime DEFAULT NULL,
                              `aid` bigint NOT NULL,
                              `md5` varchar(100) DEFAULT NULL,
                              `user_id` int DEFAULT NULL,
                              `is_free` int NOT NULL DEFAULT '2',
                              `status` int DEFAULT '2',
                              PRIMARY KEY (`id`),
                              KEY `user_image_aid_IDX` (`aid`) USING BTREE,
                              KEY `user_image_md5_IDX` (`md5`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户上传的图片';


-- gallery.user_setting_vip definition

CREATE TABLE `user_setting_vip` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `user_id` bigint DEFAULT NULL,
                                    `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `rank` int NOT NULL DEFAULT '0' COMMENT '等级',
                                    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                    `price` double NOT NULL DEFAULT '0' COMMENT '价格',
                                    `intro` varchar(300) DEFAULT NULL COMMENT '简介',
                                    `time_long` int NOT NULL DEFAULT '0' COMMENT '时间长度',
                                    `time_type` int NOT NULL DEFAULT '0' COMMENT '时间类型  1天 2月 3年 4永',
                                    `count_buy` int NOT NULL DEFAULT '0' COMMENT '购买人数',
                                    `introduce` varchar(500) DEFAULT NULL COMMENT '详细',
                                    `title` varchar(100) DEFAULT NULL COMMENT '标题',
                                    `status` int NOT NULL DEFAULT '2',
                                    `charge` int NOT NULL DEFAULT '1',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户vip';


-- gallery.user_video definition

CREATE TABLE `user_video` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `is_free` int NOT NULL DEFAULT '2' COMMENT '是否免费',
                              `create_time` datetime DEFAULT NULL,
                              `aid` bigint DEFAULT NULL,
                              `md5` varchar(100) DEFAULT NULL,
                              `img_url` varchar(100) DEFAULT NULL,
                              `user_id` int NOT NULL,
                              `status` int NOT NULL DEFAULT '0' COMMENT '状态 0 上传成功，等待转码 1转码成功',
                              `source_url` varchar(100) DEFAULT NULL COMMENT '转码前地址',
                              `duration` bigint NOT NULL DEFAULT '0',
                              PRIMARY KEY (`id`),
                              KEY `user_vedio_aid_IDX` (`aid`) USING BTREE,
                              KEY `user_vedio_md5_IDX` (`md5`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gallery.user_vip definition

CREATE TABLE `user_vip` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `user_id` bigint DEFAULT NULL,
                            `user_name` varchar(100) DEFAULT NULL,
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `ranks` int NOT NULL DEFAULT '0' COMMENT '等级',
                            `expiration_time` datetime DEFAULT NULL COMMENT '过期时间',
                            `vip_user_id` bigint DEFAULT NULL COMMENT 'vip的用户id',
                            `vip_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'vip的用户名称',
                            `title` varchar(100) DEFAULT NULL,
                            `vid` int NOT NULL,
                            `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户vip';


-- gallery.user_withdraw definition

CREATE TABLE `user_withdraw` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint DEFAULT NULL,
                                 `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `update_time` datetime DEFAULT NULL COMMENT '提现时间',
                                 `amount` double NOT NULL DEFAULT '0' COMMENT '提现金额',
                                 `status` int NOT NULL DEFAULT '0' COMMENT '状态 1.待提现  2提现完成 3提现失败',
                                 `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'email',
                                 `bank_name` varchar(100) DEFAULT NULL COMMENT '银行名称',
                                 `withdraw_name` varchar(100) DEFAULT NULL COMMENT '提现名称',
                                 `explanation` varchar(100) DEFAULT NULL COMMENT '说明',
                                 `withdraw_type` int NOT NULL DEFAULT '1' COMMENT '提现类别 1.paypal 2.银行转',
                                 `amount_received` double NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户提现记录';


-- gallery.work_order definition

CREATE TABLE `work_order` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `title` varchar(100) DEFAULT NULL,
                              `explanation` varchar(500) DEFAULT NULL COMMENT '说明',
                              `imgUrls` json DEFAULT NULL,
                              `status` int NOT NULL DEFAULT '2' COMMENT '2 待处理  1已处理 3拒绝',
                              `update_time` datetime DEFAULT NULL,
                              `create_time` datetime DEFAULT NULL,
                              `user_id` int DEFAULT NULL,
                              `user_name` varchar(100) DEFAULT NULL,
                              `reply` varchar(100) DEFAULT NULL COMMENT '回复',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='服务工单';