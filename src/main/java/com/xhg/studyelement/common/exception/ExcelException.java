package com.xhg.studyelement.common.exception;

/**
 * 表格处理异常类
 */
public final class ExcelException extends Exception{
    private static final long serialVersionUID = 4057608403796027088L;

    public static final Integer READ_ERROR = 9999;

    private final Integer code;

    public ExcelException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
