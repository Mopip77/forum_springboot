package tech.mopip77.community.community.exception;

public class CustomizeException extends RuntimeException {
    // 继承 RuntimeException 不必在上一级捕获异常, 可以只在 CustomizeExceptionHandler 捕获

    private Integer code;
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


}
