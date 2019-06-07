package tech.mopip77.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在了, 要不换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题和评论"),
    NOT_LOGIN_IN(2003, "当前操作需要登录, 请登陆后尝试"),
    SYSTEM_ERROR(2004, "服务冒烟了,要不然稍后试试!"),
    TYPE_PARAM_WRONG(2005, "评论类型不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在, 要不换个试试? ");

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
