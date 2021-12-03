package com.example.redistest.util;

/**
 * @author liu wen cheng
 * @date 2021/12/3 9:21 上午
 */
public enum ResultCodeEnum {
    /*** 通用部分响应 100 - 599***/
    // 成功请求
    SUCCESS(200, "SUCCESS"),
    // 重定向
    REDIRECT(301, "REDIRECT"),
    // 错误请求
    BAD_REQUEST(400, "BAD REQUEST"),
    // 未授权
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    // 禁止访问
    FORBIDDEN(403, "FORBIDDEN"),
    // 资源未找到
    NOT_FOUND(404, "NOT FOUND"),
    // 不允许使用该方法
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED"),
    // 请求超时
    REQUEST_TIMEOUT(408, "REQUEST_TIMEOUT"),
    // 已失效
    GONE(410, "GONE"),
    // 服务器错误
    SERVER_ERROR(500,"SERVER ERROR");

    // 500～1999 区间表示用户自定义错误
    // 1000～1999 ..............错误
    // 2000～2999 ..............错误
    // ......

    /**
     * 响应状态码
     */
    private final Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
