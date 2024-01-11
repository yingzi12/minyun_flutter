package com.xinshijie.gallery.common;

/**
 * 缓存的key 常量
 *
 * @author xinshijie
 */
public class CacheConstants {
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    public static final String LOGIN_TOKEN_MODILE_KEY = "login_modile_tokens";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    public static final String USER_ALBUM_SEE = "user:album:";


    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    public static final String EMAIL = "email:";

    public static final String PASSWORD = "password:";

    public static final String COMMENT = "comment:";

    public static final String DISCUSS = "discuss:";

    public static final String DISREPLY = "disreply:";
}
