package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求实体-查询支付结果
 * @author fenrir
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryOrderRequestEntity {

	/**
	 * appid（必填）
	 */
	private String appid;
	
	/**
	 * 商户号（必填）
	 */
	private String mch_id;

	/**
	 * 微信订单号（必填）transaction_id,out_trade_no	二选一必填，微信的订单号，建议优先使用
	 */
	private String transaction_id;

	/**
	 * 商户订单号（必填）transaction_id,out_trade_no	二选一必填
	 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一，详见商户订单号
	 */
	private String out_trade_no;
	
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

}