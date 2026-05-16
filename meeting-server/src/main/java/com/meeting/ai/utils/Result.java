package com.meeting.ai.utils;

import com.meeting.ai.enums.ErrorCode;
import lombok.Data;

import java.time.Instant;

@Data
public class Result<T> {
    private int code;
    private T data;
    private String message;
    private long timestamp;

    public Result() {
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(ErrorCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = ok();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
