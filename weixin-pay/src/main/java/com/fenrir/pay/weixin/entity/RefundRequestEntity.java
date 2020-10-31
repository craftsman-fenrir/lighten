package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求实体-申请退款
 * @author fenrir
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundRequestEntity {

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
     * 签名类型（可选）
     */
    private String sign_type;

    /**
     * 微信订单号（二选一） 微信生成的订单号
     */
    private String transaction_id;

    /**
     * 商户订单号（二选一） 商户系统内部订单号
     */
    private String out_trade_no;

    /**
     * 商户退款单号（必填） 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔
     */
    private String out_refund_no;

    /**
     * 订单金额（必填） 订单总金额，单位为分，只能为整数，详见支付金额
     */
    private Long total_fee;

    /**
     * 退款金额（必填） 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     */
    private Long refund_fee;

    /**
     * 退款货币种类（可选）
     */
    private String refund_fee_type;

    /**
     * 退款原因（可选）
     */
    private String refund_desc;

    /**
     * 退款资金来源（可选）
     */
    private String refund_account;

    /**
     * 退款结果通知url（可选）
     */
    private String notify_url;

}