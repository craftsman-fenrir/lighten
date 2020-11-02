package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求实体-统一下单
 * @author fenrir
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnifiedOrderRequestEntity {

	/**
	 * 商品描述（必填）商品简单描述，该字段请按照规范传递，具体请见参数规定
	 */
	private String body;
	
	/**
	 * 商户订单号（必填）商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
	 */
	private String out_trade_no;
	
	/**
	 * 标价金额（必填）订单总金额，单位为分，详见支付金额
	 */
	private Long total_fee;
	
	/**
	 * 终端IP（必填）支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
	 */
	private String spbill_create_ip;
	
	/**
	 * 通知地址（必填）http://www.weixin.qq.com/wxpay/pay.php
	 * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
	 */
	private String notify_url;
	
	/**
	 * 交易类型（必填）小程序取值如下：JSAPI，详细说明见参数规定
	 */
	private String trade_type;

	/**
	 * 设备号（可选）自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	 */
	private String device_info;

	/**
	 * 商品详情（可选）商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见"单品优惠参数说明"
	 */
	private String detail;
	
	/**
	 * 附加数据（可选）附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
	 */
	private String attach;
	
	/**
	 * 标价币种（可选）符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
	 */
	private String fee_type;
	
	/**
	 * 交易起始时间（可选）订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	private String time_start;
	
	/** 
	 * 交易结束时间（可选）订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
	 * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
	 * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
	 * 。其他详见时间规则建议：最短失效时间间隔大于1分钟
	 */
	private String time_expire;

	/**
	 * 订单优惠标记（可选）订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
	 */
	private String goods_tag;

	/**
	 * 指定支付方式（可选）上传此参数no_credit--可限制用户不能使用信用卡支付
	 */
	private String limit_pay;

	/**
	 * 电子发票入口开放标识（可选）Y，传入Y时，支付成功消息和支付详情页将出现开票入口。
	 * 需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
	 */
	private String receipt;

	/** 
	 * 商品ID（可选）trade_type=NATIVE时，此参数必传
	 * 此参数为二维码中包含的商品ID，商户自行定义
	 * APP支付没有这个参数,不能加进去签名
	 */
	private String product_id;

	/**
	 * 用户标识（可选）trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识,openid如何获取，可参考【获取openid】
	 * APP支付没有这个参数,不能加进去签名
	 */
	private String openid;

	/**
	 * 场景信息（可选，H5支付必填）
	 * APP支付没有这个参数,不能加进去签名
	 */
	private String scene_info;

}