package com.fenrir.pay.weixin.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付结果回调
 * @author fenrir
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class PayNotifyEntity {

	/**
	 * 返回状态码（必填）
	 */
    @JacksonXmlProperty(localName = "return_code")
    private String return_code;

    /**
     * 返回信息（必填）
     */
    @JacksonXmlProperty(localName = "return_msg")
    private String return_msg;

    // ---------------------- 以下字段在return_code为SUCCESS的时候有返回 ----------------------
    
    /**
     * appid（必填）
     */
    @JacksonXmlProperty(localName = "appid")
    private String appid;

    /**
     * 商户号（必填）
     */
    @JacksonXmlProperty(localName = "mch_id")
    private String mch_id;

    /**
     * 设备号（可选）
     */
    @JacksonXmlProperty(localName = "device_info")
    private String device_info;

    /**
     * 随机字符串（必填）随机字符串，不长于32位
     */
    @JacksonXmlProperty(localName = "nonce_str")
    private String nonce_str;

    /**
     * 签名（必填）签名，详见签名算法
     */
    @JacksonXmlProperty(localName = "sign")
    private String sign;

    /**
     * 签名类型（可选） 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    @JacksonXmlProperty(localName = "sign_type")
    private String sign_type;

    /**
     * 业务结果（必填） SUCCESS/FAIL
     */
    @JacksonXmlProperty(localName = "result_code")
    private String result_code;

    /**
     * 错误代码（可选）错误返回的信息描述
     */
    @JacksonXmlProperty(localName = "err_code")
    private String err_code;

    /**
     * 错误代码描述（可选）错误返回的信息描述
     */
    @JacksonXmlProperty(localName = "err_code_des")
    private String err_code_des;

    /**
     * 用户标识（必填）用户在商户appid下的唯一标识
     */
    @JacksonXmlProperty(localName = "openid")
    private String openid;

    /**
     * 是否关注公众账号（必填）用户是否关注公众账号，Y-关注，N-未关注
     */
    @JacksonXmlProperty(localName = "is_subscribe")
    private String is_subscribe;

    /*
     * 交易类型（必填）JSAPI/NATIVE/APP
     */
    @JacksonXmlProperty(localName = "trade_type")
    private String trade_type;

    /**
     * 付款银行（必填）CMC 银行类型，采用字符串类型的银行标识，银行类型见银行列表
     */
    @JacksonXmlProperty(localName = "bank_type")
    private String bank_type;

    /**
     * 订单金额（必填）订单总金额，单位为分
     */
    @JacksonXmlProperty(localName = "total_fee")
    private String total_fee;

    /**
     * 应结订单金额（可选）应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额
     */
    @JacksonXmlProperty(localName = "settlement_total_fee")
    private String settlement_total_fee;

    /**
     * 货币种类（可选）CNY 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    @JacksonXmlProperty(localName = "fee_type")
    private String fee_type;

    /**
     * 现金支付金额(必填) 现金支付金额订单现金支付金额，详见支付金额
     */
    @JacksonXmlProperty(localName = "cash_fee")
    private String cash_fee;

    /** 现金支付货币类型（可选）CNY 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型 */
    @JacksonXmlProperty(localName = "cash_fee_type")
    private String cash_fee_type;

    /**
     * 总代金券金额（可选）代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
     */
    @JacksonXmlProperty(localName = "coupon_fee")
    private String coupon_fee;

    /**
     * 代金券使用数量（可选）代金券使用数量
     */
    @JacksonXmlProperty(localName = "coupon_count")
    private String coupon_count;

    // ---------------------- 微信的大坑begin ----------------------
    
    /** 代金券类型(可选) CASH--充值代金券,NO_CASH---非充值代金券
     * 并且订单使用了免充值券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
     */
//    private String couponType$n;

    /** 代金券ID(必填) 代金券ID,$n为下标，从0开始编号 */
//    private String couponId$n;

    /** 单个代金券支付金额(可选) 单个代金券支付金额,$n为下标，从0开始编号 */
//    private String couponFee$n;
    
    // ---------------------- 微信的大坑end ----------------------

    /**
     * 微信支付订单号（必填）微信支付订单号
     */
    @JacksonXmlProperty(localName = "transaction_id")
    private String transaction_id;

    /**
     * 商户订单号（必填）商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     */
    @JacksonXmlProperty(localName = "out_trade_no")
    private String out_trade_no;

    /*
     * 商家数据包（可选）商家数据包，原样返回
     */
    @JacksonXmlProperty(localName = "attach")
    private String attach;

    /**
     * 支付完成时间（必填）支付完成时间
     * 格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010，其他详见时间规则
     */
    @JacksonXmlProperty(localName = "time_end")
    private String time_end;

}
