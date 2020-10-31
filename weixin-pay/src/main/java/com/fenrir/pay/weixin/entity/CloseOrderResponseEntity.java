package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关闭订单响应实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CloseOrderResponseEntity {

    /**
     * 返回状态码（必填）
     */
    private String return_code;

    /**
     * 返回信息（可选）
     */
    private String return_msg;

    /**
     * 业务结果描述（必填）
     */
    private String result_msg;

    /**
     * appid（必填）
     */
    private String appid;

    /**
     * 商户号（必填）
     */
    private String mch_id;

    /**
     * 随机字符串（必填）
     */
    private String nonce_str;

    /**
     * 签名（必填）
     */
    private String sign;

    /**
     * 业务结果（必填）
     */
    private String result_code;

    /**
     * 错误编号（可选）
     */
    private String err_code;

    /**
     * 错误编号描述（可选）
     */
    private String err_code_des;

}