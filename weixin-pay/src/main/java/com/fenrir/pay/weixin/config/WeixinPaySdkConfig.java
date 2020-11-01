package com.fenrir.pay.weixin.config;

import org.apache.http.client.HttpClient;

/**
 * 微信api常量配置
 * @author fenrir
 *
 */
public interface WeixinPaySdkConfig {

	// ----------------------------------- 请求参数begin -----------------------------------
	
	/**
	 * 签名
	 */
	String FIELD_SIGN = "sign";
	
	/**
	 * 签名类型
	 */
	String FIELD_SIGN_TYPE = "sign_type";
	
	/**
	 * 小程序ID
	 */
	String FIELD_APPID = "appid";
	
	/**
	 * 商户ID
	 */
	String FIELD_MCH_ID = "mch_id";
	
	/**
	 * 随机字符串
	 */
	String FIELD_NONCE_STR = "nonce_str";
	
	/**
	 * 回调地址
	 */
	String FIELD_NOTIFY_URL = "notify_url";
	
	// ----------------------------------- 请求参数end -----------------------------------
	
	// ----------------------------------- 调用结果begin -----------------------------------

	/**
	 * 成功
	 */
	String SUCCESS  = "SUCCESS";
	
	/**
	 * 失败
	 */
	String FAIL = "FAIL";
	
	/**
	 * return_code
	 */
	String FIELD_RETURN_CODE = "return_code";
	
	/**
	 * return_msg
	 */
	String FIELD_RETURN_MSG = "return_msg";
	
	// ----------------------------------- 调用结果end -----------------------------------
	
	// ----------------------------------- 支付方式begin -----------------------------------

	/**
	 * jsapi支付 或 小程序支付
	 */
	String JSAPI = "JSAPI";
	
	/**
	 * native支付
	 */
	String NATIVE = "NATIVE";
	
	/**
	 * app支付
	 */
	String APP = "APP";
	
	/**
	 * h5支付
	 */
	String H5 = "MWEB";
	
	// ----------------------------------- 支付方式end -----------------------------------
	
	// ----------------------------------- 支付状态begin -----------------------------------

	/**
	 * 未支付
	 */
	String NOTPAY = "NOTPAY";
	
	/**
	 * 转入退款
	 */
	String REFUND = "REFUND";
	
	/**
	 * 已关闭
	 */
	String CLOSED = "CLOSED";
	
	/**
	 * 已撤销(付款码支付)
	 */
	String REVOKED = "REVOKED";
	
	/**
	 * 用户支付中(付款码支付)
	 */
	String USERPAYING = "USERPAYING";
	
	/**
	 * 支付失败(其他原因，如银行返回失败)
	 */
	String PAYERROR = "PAYERROR";
	
	// ----------------------------------- 支付状态end -----------------------------------
	
	// ----------------------------------- 微信支付地址begin -----------------------------------
	
	/**
	 * 主api
	 */
	String DOMAIN_API = "api.mch.weixin.qq.com";
	
	/**
	 * 备用api
	 */
	String DOMAIN_API2 = "api2.mch.weixin.qq.com";
	
	/**
	 * 香港(澳门,台湾?)的服务器需要使用该api
	 */
	String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
	
	/**
	 * 国外的服务器需要使用该api
	 */
	String DOMAIN_APIUS = "apius.mch.weixin.qq.com";
	
	// ----------------------------------- 微信支付地址end -----------------------------------

	// ----------------------------------- 微信支付接口uri begin -----------------------------------

	/**
	 * 扫码支付
	 */
	String MICROPAY_URL_SUFFIX = "/pay/micropay";
	
	/**
	 * 统一下单
	 */
	String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
	
	/**
	 * 查询订单
	 */
	String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
	
	/**
	 * 撤销订单
	 */
	String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
	
	/**
	 * 关闭订单
	 */
	String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";
	
	/**
	 * 申请退款
	 */
	String REFUND_URL_SUFFIX = "/secapi/pay/refund";
	
	/**
	 * 退款查询
	 */
	String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";
	
	/**
	 * 对账单下载
	 */
	String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
	
	/**
	 * 交易保障
	 */
	String REPORT_URL_SUFFIX = "/payitil/report";
	
	/**
	 * 转换短链接
	 */
	String SHORTURL_URL_SUFFIX = "/tools/shorturl";
	
	/**
	 * 授权查询openid
	 */
	String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";
	
	// ----------------------------------- 微信支付接口uri end -----------------------------------
	
	// ----------------------------------- 微信支付沙盒环境接口uri begin -----------------------------------
	
	/**
	 * 扫码支付
	 */
	String SANDBOX_MICROPAY_URL_SUFFIX = "/sandboxnew/pay/micropay";
	
	/**
	 * 统一下单
	 */
	String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
	
	/**
	 * 查询订单
	 */
	String SANDBOX_ORDERQUERY_URL_SUFFIX = "/sandboxnew/pay/orderquery";
	
	/**
	 * 撤销订单
	 */
	String SANDBOX_REVERSE_URL_SUFFIX = "/sandboxnew/secapi/pay/reverse";
	
	/**
	 * 关闭订单
	 */
	String SANDBOX_CLOSEORDER_URL_SUFFIX = "/sandboxnew/pay/closeorder";
	
	/**
	 * 申请退款
	 */
	String SANDBOX_REFUND_URL_SUFFIX = "/sandboxnew/secapi/pay/refund";
	
	/**
	 * 退款查询
	 */
	String SANDBOX_REFUNDQUERY_URL_SUFFIX = "/sandboxnew/pay/refundquery";
	
	/**
	 * 对账单下载
	 */
	String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
	
	/**
	 * 交易保障
	 */
	String SANDBOX_REPORT_URL_SUFFIX = "/sandboxnew/payitil/report";
	
	/**
	 * 转换短链接
	 */
	String SANDBOX_SHORTURL_URL_SUFFIX = "/sandboxnew/tools/shorturl";
	
	/**
	 * 授权查询openid
	 */
	String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
	
	// ----------------------------------- 微信支付沙盒环境接口uri end -----------------------------------
	
	// ----------------------------------- 其他参数begin -----------------------------------

	// 加密方式
	String HMACSHA256 = "HMAC-SHA256";
	String MD5 = "MD5";
	
	// 协议
	String TLS = "tls";
	String TLS_V1 = "TLSv1";
	String HTTP = "http";
	String HTTPS = "https";
		
	// 请求前缀
	String HTTP_PREFIX = "http://";
	String HTTPS_PREFIX = "https://";
		
	// 编码方式
	String UTF_8 = "UTF-8";
	
	/**
	 * 所谓的微信支付SDK版本
	 */
	String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
	
	/**
	 * 是否为主域名
	 */
	boolean IS_PRIMARY_DOMAIN = true;
	
	/**
	 * http请求头key
	 */
	String CONTENT_TYPE_KEY = "Content-Type";
	
	/**
	 * http请求投value
	 */
	String CONTENT_TYPE_VALUE = "text/xml";
	
	/**
	 * http请求头key
	 */
	String USER_AGENT_KEY = "User-Agent";
	
	/**
	 * 用户代理
	 */
	String USER_AGENT = new StringBuffer(WXPAYSDK_VERSION)
		.append(" (").append(System.getProperty("os.arch"))
		.append(" ").append(System.getProperty("os.name"))
		.append(" ").append(System.getProperty("os.version"))
		.append(") Java/").append(System.getProperty("java.version"))
		.append(" HttpClient/")
		.append(HttpClient.class.getPackage().getImplementationVersion()).toString();

	// ----------------------------------- 其他参数end -----------------------------------
}