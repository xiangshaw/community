package plus.axz.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    PARAM_INVALID(501,"无效参数"),
    QUESTION_NOT_FOUND(2001, "这个问题不见了，换一个试试？"),
    COMMENT_NOT_FOUND(2002, "这个回复不见了，换一个试试？"),
    LOGIN_CONNECT_ERROR(2003, "第三方登录连接出了问题，再试一次？"),
    USER_NOT_LOGIN(2004, "您还未登录，请登录后再试试？"),
    TYPE_NOT_EXIST(2005, "评论的类型错误或不存在"),
    COMMENT_PARENT_NOT_EXIST(2006, "未选中任何问题或回复进行评论"),
    SYSTEM_ERROR(2007, "服务器冒烟儿了，稍后再试试吧~"),
    CONTENT_IS_EMPTY(2008, "不能输入空的内容哦~"),
    NOTIFICATION_TYPE_NOT_EXIST(2009, "不能输入空的内容哦~"),
    READ_NOTIFICATION_FAIL(2010, "账号异常，请重新登陆！"),
    UPLOAD_FILE_ERROR(2011, "上传出错了，请检查你的文件格式！"),
    IS_NOT_LEGAL(2012, "需要先登录"),
    SEND_EMAIL_FAIL(2013, "邮件发送失败"),
    EMAIL_ALREADY_EXISTS(2014, "邮箱已注册"),
    PWD_NOT_EQUAL(2015, "两次输入的密码不一致"),
    EMAIL_NOT_EXISTS(2016, "邮箱未注册"),
    ;

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
