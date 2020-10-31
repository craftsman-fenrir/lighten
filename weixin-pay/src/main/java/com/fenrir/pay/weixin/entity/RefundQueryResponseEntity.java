package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体-查询退款
 * @author fenrir
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundQueryResponseEntity {

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
     * 错误码（必填）
     */
    private String err_code;

    /**
     * 错误描述（必填）
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
     * 订单总退款次数（可选）
     */
    private Long total_refund_count;

    /**
     * 微信订单号（必填）
     */
    private String transaction_id;

    /**
     * 商户订单号（必填）
     */
    private String out_trade_no;

    /**
     * 订单金额（必填）
     */
    private Long total_fee;

    /**
     * 应结订单金额（可选）
     */
    private Long settlement_total_fee;

    /**
     * 货币种类（可选）
     */
    private String fee_type;

    /**
     * 现金支付金额（必填）
     */
    private Long cash_fee;

    /**
     * 退款笔数（必填）
     */
    private Integer refund_count;
    
//    商户退款单号	out_refund_no_$n	是	String（64）	1217752501201407033233368018	商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
//    微信退款单号	refund_id_$n	是	String（32）	1217752501201407033233368018	微信退款单号
//    退款渠道	refund_channel_$n	否	String（16）	ORIGINAL
//    ORIGINAL—原路退款
//
//    BALANCE—退回到余额
//
//    OTHER_BALANCE—原账户异常退到其他余额账户
//
//    OTHER_BANKCARD—原银行卡异常退到其他银行卡
//
//    申请退款金额	refund_fee_$n	是	Int	100	退款总金额,单位为分,可以做部分退款
//    退款金额	settlement_refund_fee_$n	否	Int	100	退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
//    代金券类型	coupon_type_$n_$m	否	String（8）	CASH
//    CASH--充值代金券
//    NO_CASH---非充值优惠券
//
//    开通免充值券功能，并且订单使用了优惠券后有返回（取值：CASH、NO_CASH）。$n为下标,$m为下标,从0开始编号，举例：coupon_type_$0_$1
//
//    总代金券退款金额	coupon_refund_fee_$n	否	Int	100	代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
//    退款代金券使用数量	coupon_refund_count_$n	否	Int	1	退款代金券使用数量 ,$n为下标,从0开始编号
//    退款代金券ID	coupon_refund_id_$n_$m	否	String（20）	10000 	退款代金券ID, $n为下标，$m为下标，从0开始编号
//    单个代金券退款金额	coupon_refund_fee_$n_$m	否	Int	100	单个退款代金券支付金额, $n为下标，$m为下标，从0开始编号
//    退款状态	refund_status_$n	是	String（16）	SUCCESS
//    退款状态：
//
//    SUCCESS—退款成功
//
//    REFUNDCLOSE—退款关闭。
//
//    PROCESSING—退款处理中
//
//    CHANGE—退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。$n为下标，从0开始编号。
//
//    退款资金来源	refund_account_$n	否	String（30）	REFUND_SOURCE_RECHARGE_FUNDS
//    REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款/基本账户
//
//    REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款
//
//    $n为下标，从0开始编号。
//
//    退款入账账户	refund_recv_accout_$n	是	String（64）	招商银行信用卡0403	取当前退款单的退款入账方
//    退款成功时间	refund_success_time_$n	否	String（20）	2016-07-25 15:26:26	退款成功时间，当退款状态为退款成功时有返回。$n为下标，从0开始编号。

}