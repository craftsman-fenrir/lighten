package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体-查询订单
 * @author fenrir
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryOrderResponseEntity {

	/**
	 * 返回状态码（必填）此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断
	 */
	private String return_code;

	/**
	 * 返回信息（必填）错误信息
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
	 * 随机字符串（必填）随机字符串，不长于32位。推荐随机数生成算法
	 */
	private String nonce_str;
	
	/**
	 * 签名（必填）签名，详见签名生成算法
	 */
	private String sign;
	
	/**
	 * 业务结果（必填）SUCCESS/FAIL
	 */
	private String result_code;
	
	/**
	 * 错误代码（可选）当result_code为FAIL时返回错误代码，详细参见下文错误列表
	 */
	private String err_code;

	/**
	 * 错误代码描述（可选）当result_code为FAIL时返回错误描述，详细参见下文错误列表
	 */
	private String err_code_des;
	
	// -------- 以下字段在return_code， result_code，trade_state为SUCCESS的时候有返回 --------
	// -------- 如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传） --------
	
	/**
	 * 设备号（可选）微信支付分配的终端设备号
	 */
	private String device_info;

	/**
	 * 用户标识（必填）用户在商户appid下的唯一标识
	 */
	private String openid;
	
	/**
	 * 是否关注公众账号（必填）用户是否关注公众账号，Y-关注，N-未关注
	 */
	private String is_subscribe;
	
	/**
	 * 交易类型（必填）调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定
	 */
	private String trade_type;
	
	/**
	 * 交易状态（必填）支付状态机请见下单api页面
	 */
	private String trade_state;
	
	/**
	 * 付款银行（必填）银行类型，采用字符串类型的银行标识
	 */
	private String bank_type;
	
	/**
	 * 标价金额（必填）订单总金额，单位为分
	 */
	private Integer total_fee;
	
	/**
	 * 应结订单金额（可选）当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额
	 */
	private Integer settlement_total_fee;
	
	/**
	 * 标价币种（可选）货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String fee_type;
	
	/**
	 * 现金支付金额（必填）现金支付金额订单现金支付金额，详见支付金额
	 */
	private Integer cash_fee;
	
	/**
	 * 现金支付币种（可选）货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String cash_fee_type;
	
	/**
	 * 代金券金额（可选）"代金券"金额<=订单金额，订单金额-"代金券"金额=现金支付金额，详见支付金额
	 */
	private Integer coupon_fee;
	
	/**
	 * 代金券使用数量（可选）代金券使用数量
	 */
	private Integer coupon_count;
	
	// ---------------------- 微信的大坑begin ----------------------
	
	/** 代金券类型（可选） */
	//private String couponType_$n;
	
	/** 代金券ID（可选） 代金券ID, $n为下标，从0开始编号 */
	//private String couponId_$n;
	
	/** 单个代金券支付金额（可选） 单个代金券支付金额, $n为下标，从0开始编号 */
	//private Integer couponFee_$n;
	
	// ---------------------- 微信的大坑end ----------------------
	
	/**
	 * 微信支付订单号（必填） 微信支付订单号
	 */
	private String transaction_id;
	
	/**
	 * 商户订单号（必填） 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
	 */
	private String out_trade_no;
	
	/**
	 * 附加数据（可选） 附加数据，原样返回
	 */
	private String attach;
	
	/**
	 * 支付完成时间（必填） 订单支付时间
	 * 格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	private String time_end;
	
	/**
	 * 交易状态描述（必填） 对当前查询订单状态的描述和下一步操作的指引
	 */
	private String trade_state_desc;

}