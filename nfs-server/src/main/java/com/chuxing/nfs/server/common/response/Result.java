package com.chuxing.nfs.server.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2022/6/27 20:00
 * @author huangchenguang
 * @desc result
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * @date 2022/6/27 20:01
     * @author huangchenguang
     * @desc data
     */
    private T data;

    /**
     * @date 2022/6/27 20:01
     * @author huangchenguang
     * @desc msg
     */
    private String msg;

    /**
     * @date 2022/6/27 20:05
     * @author huangchenguang
     * @desc code
     */
    private Integer code;

    /**
     * @date 2022/6/27 20:10
     * @author huangchenguang
     * @desc get success data
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(T data) {
        return (Result<T>) Result.builder().data(data).msg("success").code(200).build();
    }

    /**
     * @date 2022/6/27 20:10
     * @author huangchenguang
     * @desc get fail data
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> fail(T data, String msg) {
        return (Result<T>) Result.builder().data(data).msg(msg).code(500).build();
    }

}
