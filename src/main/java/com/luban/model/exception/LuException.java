package com.luban.model.exception;

public class LuException extends RuntimeException {
    private static final long serialVersionUID = 6958499248468627021L;
    /** 错误码 */
    private int errorCode;
    /** 错误上下文 */
    private String errorMessage;

    public LuException(int errorCode,String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public LuException(LuExceptionEnum systemError, String errorMessage){

    }

    public LuException(int errorCode){
        this.errorCode = errorCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

