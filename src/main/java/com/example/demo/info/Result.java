package com.example.demo.info;

import com.example.demo.common.Status;

import java.text.MessageFormat;

/**
 * result
 *
 * @param <T> T
 */
public class Result<T> {
    /**
     * status
     */
    private Integer code;

    /**
     * message
     */
    private String msg;

    /**
     * data
     */
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(T data) {
        this.code  = 0;
        this.data = data;
    }

    private Result(Status status) {
        if (status != null) {
            this.code = status.getCode();
            this.msg = status.getMsg();
        }
    }

    /**
     * Call this function if there is success
     *
     * @param data data
     * @param <T> type
     * @return resule
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * Call this function if there is any error
     *
     * @param status status
     * @return result
     */
    public static Result error(Status status) {
        return new Result(status);
    }

    /**
     * Call this function if there is any error
     *
     * @param messgae String
     * @return result
     */
    public static Result error(String messgae) {
        Result result = new Result();
        result.setMsg(messgae);
        return result;
    }

    /**
     * Call this function if there is any error
     *
     * @param status status
     * @param args args
     * @return result
     */
    public static Result errorWithArgs(Status status, Object... args) {
        return new Result(status.getCode(), MessageFormat.format(status.getMsg(), args));
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Status{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
