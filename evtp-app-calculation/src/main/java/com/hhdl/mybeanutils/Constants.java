package com.hhdl.mybeanutils;


import java.util.concurrent.TimeUnit;

public class Constants {


    /**
     * 保存在http session中的用户对象
     */
    public static final String USER_IN_SESSION = "userInSession";

    /**
     * 实体创建时间属性
     */
    public static final String CREATE_TIME = "createTime";

    /**
     * 实体更新时间属性
     */
    public static final String UPDATE_TIME = "updateTime";

    /**
     * 实体删除属性
     */
    public static final String DEL_FLAG = "delFlag";

    /**
     * XSESSION
     */
    public static final String XSESSION = "XSESSION";

    /**
     * Redis Web用户 session前缀符
     */
    public static final String REDIS_WEB_PREFIX = "W";

    /**
     * Web用户COOKIE用户ID
     */
    public static final String WEB_COOKIE_UID = REDIS_WEB_PREFIX + "UID";

    /**
     * Redis Session失效时间
     */
    public static final Long MAX_SESSION_INACTIVE_INTERVAL = 3600L * 8;

    /**
     * Redis Session失效时间单位
     */
    public static final TimeUnit SESSION_PERIOD_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 默认当前页码
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认一页显示数据条数
     */
    public static int DEFAULT_PAGE_SIZE = 10;

    /**
     * 验证码标识
     */
    public static final String VALID_CODE = "validCode";

    /**
     * 删除标识：正常
     */
    public static final int DEL_FLAG_NORMAL = 0;

    /**
     * 信访投诉处理表示
     */
    public static final int CLOSE_FLAG_NORMAL = 0;
    public static final int CLOSE_FLAG_CLOSED = 1;
    /**
     * 删除标识：已删除
     */
    public static final int DEL_FLAG_DELETED = 1;

    /**
     * JSON数据返回成功信息提示
     */
    public static String JSON_BACK_SUCCESS = "成功";

    /**
     * JSON数据返回失败信息提示
     */
    public static String JSON_BACK_FAILURE = "失败";

    /**
     * 返回失败的提示信息的key
     */
    public static final String FAILURE_KEY = "error";


    /**
     * APP接口请求时间戳字段
     */
    public static final String APP_TIME_STAMP = "timeSp";

    /**
     * 接口签名验证失败
     */
    public static final String APP_SIGN_ERROR = "接口签名验证失败";

    /**
     * api Token
     */
    public static final String APP_TOKEN = "token";

    /**
     * Token失效时间单位
     */
    public static final TimeUnit APP_TOKEN_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 登录状态过期
     */
    public static String APP_LOGIN_EXPIRED = "登录已过期，请重新登录";

    /**
     * 非法访问
     */
    public static String APP_ILLEGALITY_LOGIN = "非法访问，请先登录";


    /**
     * RESPONSE输出JSON类型
     */
    public static final String JSON_CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * RESPONSE输出文本类型
     */
    public static final String TEXT_CONTENT_TYPE = "application/text;charset=utf-8";

    /**
     * UTF-8编码
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 英文状态逗号
     */
    public static final String COMMA = ",";


    /**
     * 英文状态竖线分隔符
     */
    public static final String PIPE = "\\|";

    public static final char SLASH = '/';

    /**
     * ISO-8859-1编码
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 用户－角色－菜单
     */
    public static final String USER_ROLE_MENU_IN_SESSION = "userRoleMenu";


    /**
     * 表单提交查询对象FormQuery时在表单中的提交字段
     */
    public static final String EXPORT_FORM_SUMIT_FIELD = "query";

    /**
     * 显示在日历中的日程的颜色
     */
    public static final String SCHEDULE_COLOR = "MediumPurple";
    /**
     * 显示在日历中的日志的颜色
     */
    public static final String JOURNAL_COLOR = "LimeGreen";

    /**
     * 日志的提交在今天9点之前都算昨天
     */
    public static Integer JOURNAL_END = -9;

    /**
     * 值认的字典值枚举值
     */
    public static Integer DEFAULT_MATA_VALUE_ENUM = 0;

    /**
     * 消息内容最长字数
     */
    public static int MAX_NOTICE_LENGTH = 500;

    /**
     * 允许上传的文件类型
     */
    public static String[] ALLOW_EXTS = new String[]{"png", "jpg", "jpeg", "gif", "bmp", "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "rar", "zip", "ceb"};

    /**
     * 网盘文件下载URL前缀
     */
    public static String DISK_FILE_URL_PREFIX = "/admin/disk/file/download";

    /**
     * 允许导入上传的文件类型
     */
    public static String[] ALLOW_IMPORT_EXTS = new String[]{"xlsx"};


    /**
     * 信访是否处理常量
     */
    public static final Integer LETTER_OPEN = 0;
    public static final Integer LETTER_CLOSE = 1;
    /**
     * 是否启用
     */
    public static final Integer START = 1;//启用
    public static final Integer STOP = 0;//停用

    /**
     * 中文 是
     */
    public static final String CN_YES = "是";

    /**
     * 中文 否
     */
    public static final String CN_NO = "否";

    /**
     * 是否是市控点位  0否1是
     */
    public static final Integer ADMINISTRATION_LEVEL_0 = 0;
    public static final Integer ADMINISTRATION_LEVEL_1 = 1;

}
