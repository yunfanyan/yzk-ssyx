package com.yzk.common.exception;

import com.yzk.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @description: 自定义异常处理
 * @date: 2026-10-02 11:34
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Data
public class SsyxException extends RuntimeException{
    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public SsyxException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public SsyxException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}