package com.fenrir.pay.weixin.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退款通知
 * @author fenrir
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class RefundNotifyEntity {
	
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
	
	/**
	 * 公众账号ID（必填）
	 */
	@JacksonXmlProperty(localName = "appid")
	private String appid;
	
	/**
	 * appid（必填）
	 */
	@JacksonXmlProperty(localName = "mch_id")
	private String mch_id;
	
	/**
	 * 随机字符串（必填）
	 */
	@JacksonXmlProperty(localName = "nonce_str")
	private String nonce_str;
	
	/**
	 * 加密信息（必填）
	 */
	@JacksonXmlProperty(localName = "req_info")
	private String req_info;
	
	/**
	 * 微信订单号（必填）
	 */
	@JacksonXmlProperty(localName = "transaction_id")
	private String transaction_id;
	
	/**
	 * 商户订单号（必填）
	 */
	@JacksonXmlProperty(localName = "out_trade_no")
	private String out_trade_no;
	
	/**
	 * 微信退款单号（必填）
	 */
	@JacksonXmlProperty(localName = "refund_id")
	private String refund_id;
	
	/**
	 * 商户退款单号（必填）
	 */
	@JacksonXmlProperty(localName = "out_refund_no")
	private String out_refund_no;
	
	/**
	 * 订单金额（必填）
	 */
	@JacksonXmlProperty(localName = "total_fee")
	private String total_fee;
	
	/**
	 * 应结订单金额（可选）
	 */
	@JacksonXmlProperty(localName = "settlement_total_fee")
	private String settlement_total_fee;
	
	/**
	 * 申请退款金额（可选）
	 */
	@JacksonXmlProperty(localName = "refund_fee")
	private String refund_fee;
	
	/**
	 * 退款金额（可选）
	 */
	@JacksonXmlProperty(localName = "settlement_refund_fee")
	private String settlement_refund_fee;
	
	/**
	 * 退款状态（可选）
	 */
	@JacksonXmlProperty(localName = "refund_status")
	private String refund_status;
	
	/**
	 * 退款成功时间（可选）
	 */
	@JacksonXmlProperty(localName = "success_time")
	private String success_time;
	
	/**
	 * 退款入账账户（必填）
	 */
	@JacksonXmlProperty(localName = "refund_recv_accout")
	private String refund_recv_accout;
	
	/**
	 * 退款资金来源（必填）
	 */
	@JacksonXmlProperty(localName = "refund_account")
	private String refund_account;
	
	/**
	 * 退款发起来源（必填）
	 */
	@JacksonXmlProperty(localName = "refund_request_source")
	private String refund_request_source;
	
	/**
	 * 数据库字段，创建时间
	 */
	private String create_time;
	
}