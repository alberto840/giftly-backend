package com.diplomado.diplomado.utils;

import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
    private T data;

    public ResponseDto() {
    }

    public ResponseDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
