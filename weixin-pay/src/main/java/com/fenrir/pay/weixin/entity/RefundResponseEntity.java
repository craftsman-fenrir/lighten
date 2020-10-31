package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体-申请退款
 * @author fenrir
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundResponseEntity {

    /**
     * 返回状态码（必填）
     */
    private String return_code;

    /**
     * 返回信息（必填）
     */
    private String return_msg;

    /**
     * 业务结果（必填）
     */
    private String result_code;

    /**
     * 错误代码（可选）
     */
    private String err_code;

    /**
     * 错误代码描述（可选）
     */
    private String err_code_des;

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
     * 微信订单号（必填）
     */
    private String transaction_id;

    /**
     * 商户订单号（必填）
     */
    private String out_trade_no;

    /**
     * 微信退款单号（必填）
     */
    private String refund_id;

    /**
     * 退款金额（必填）
     */
    private Long refund_fee;

    /**
     * 应结退款金额（可选）
     */
    private Long settlement_refund_fee;

    /**
     * 标价金额（必填）
     */
    private Long total_fee;

    /**
     * 应结订单金额（可选）
     */
    private Long settlement_total_fee;

    /**
     * 标价币种（可选）
     */
    private String fee_type;

    /**
     * 现金支付金额（必填）
     */
    private Long cash_fee;

    /**
     * 现金支付币种（可选）
     */
    private String cash_fee_type;

    /**
     * 现金退款金额（可选）
     */
    private Long cash_refund_fee;

    /**
     * 代金券退款总金额（可选）
     */
    private Long coupon_refund_fee;

    /**
     * 退款代金券使用数量（可选）
     */
    private Integer coupon_refund_count;

}