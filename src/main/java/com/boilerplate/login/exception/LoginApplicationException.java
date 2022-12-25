package com.boilerplate.login.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginApplicationException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public LoginApplicationException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if(message == null){
            return errorCode.getMessage();
        }
        return String.format("%s %s",errorCode,getMessage(),message);
    }
}
