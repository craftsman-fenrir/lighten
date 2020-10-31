package com.fenrir.pay.weixin.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体-统一下单
 * @author fenrir
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnifiedOrderResponseEntity {

	/**
	 * JSAPI二次签名用的临时字段
	 */
	private String time_stamp;

	/**
	 * 返回状态码（必填）SUCCESS/FAIL
	 */
	private String return_code;

	/**
	 * 返回信息（扫码支付:必填，JSAPI支付:必填，小程序支付:可选，H5支付:必填，APP支付:可选） 返回信息，如非空，为错误原因
	 */
	private String return_msg;

	// ---------------------- 以下字段在return_code为SUCCESS的时候有返回 ----------------------

	/**
	 * appid（必填）
	 */
	private String appid;

	/**
	 * 商户号（必填）
	 */
	private String mch_id;

	/**
	 * 设备号（可选） 自定义参数，可以为请求支付的终端设备号等
	 */
	private String device_info;

	/**
	 * 随机字符串（必填） 微信返回的随机字符串
	 */
	private String nonce_str;

	/**
	 * 签名（必填） 微信返回的签名值，详见签名算法
	 */
	private String sign;

	/**
	 * 业务结果（必填） SUCCESS/FAIL
	 */
	private String result_code;

	/**
	 * 错误代码（可选） 详细参见下文错误列表
	 */
	private String err_code;

	/**
	 * 错误代码描述（可选） 错误信息描述
	 */
	private String err_code_des;

	// ---------------------- 以下字段在return_code和result_code为SUCCESS的时候有返回 ----------------------

	/**
	 * 交易类型（必填） 交易类型，取值为：JSAPI，NATIVE，APP等，说明详见参数规定
	 */
	private String trade_type;

	/**
	 * 预支付交易会话标识（必填） 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	private String prepay_id;

	/**
	 * 保存到数据库的时间
	 */
	private Timestamp create_time;

	/**
	 * 二维码链接（扫码支付必填，JSAPI，H5，APP支付为空）
	 * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
	 * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
	 */
	private String code_url;

	/**
	 * 支付跳转链接（H5支付专用，用来拉起设备上的微信客户端）
	 */
	private String mweb_url;

}