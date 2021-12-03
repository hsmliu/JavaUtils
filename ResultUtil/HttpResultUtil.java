package com.example.redistest.util;

import java.io.Serializable;

/**
 * created on 2021/11/9 8:58 上午
 * Description:
 * @author liu wen cheng
 */

public class HttpResultUtil<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    // 构造器开始
    /**
     * 无参构造器(构造器私有，外部不可以直接创建)
     */
    private HttpResultUtil() {
        this.code = 200;
        this.message = "SUCCESS";
    }
    /**
     * 有参构造器
     */
    private HttpResultUtil(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 有参构造器
     */
    private HttpResultUtil(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 有参构造器
     * @param obj
     */
    private HttpResultUtil(T obj) {
        this.code = 200;
        this.message = "SUCCESS";
        this.data = obj;
    }

    /**
     * 有参构造器
     * @param resultCode
     */
    private HttpResultUtil(ResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
    // 构造器结束

    /**
     * 通用返回成功（没有返回结果）
     * @param <T>
     * @return
     */
    public static<T> HttpResultUtil<T> success(){
        return new HttpResultUtil();
    }

    /**
     * 返回成功（有返回结果）
     * @param data
     * @param <T>
     * @return
     */
    public static<T> HttpResultUtil<T> success(T data){
        return new HttpResultUtil<T>(data);
    }

    /**
     * 无参通用返回失败
     * @param <T>
     * @return
     */
    public static<T> HttpResultUtil<T> failure(){
        return  new HttpResultUtil<T>(-1, "ERROR");
    }

    /**
     * 通用返回失败
     * @param resultCode
     * @param <T>
     * @return
     */
    public static<T> HttpResultUtil<T> failure(ResultCodeEnum resultCode){
        return  new HttpResultUtil<T>(resultCode);
    }

    /**
     * 自定义返回失败
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static<T> HttpResultUtil<T> failure(Integer code, String message){
        return  new HttpResultUtil<T>(code, message);
    }

    /**
     * 返回信息（有返回结果）
     * @param data
     * @param <T>
     * @return
     */
    public static<T> HttpResultUtil<T> info(Integer code, String message, T data){
        return new HttpResultUtil<T>(code, message, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
