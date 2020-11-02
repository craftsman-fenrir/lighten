package com.fenrir.pay.weixin;

import java.util.Map;

import com.fenrir.pay.weixin.config.WeixinPayApiConfig;
import com.fenrir.pay.weixin.config.WeixinPayMerchantConfig;
import com.fenrir.pay.weixin.config.WeixinPaySdkConfig;
import com.fenrir.pay.weixin.entity.CloseOrderResponseEntity;
import com.fenrir.pay.weixin.entity.QueryOrderResponseEntity;
import com.fenrir.pay.weixin.entity.RefundQueryRequestEntity;
import com.fenrir.pay.weixin.entity.RefundQueryResponseEntity;
import com.fenrir.pay.weixin.entity.RefundRequestEntity;
import com.fenrir.pay.weixin.entity.RefundResponseEntity;
import com.fenrir.pay.weixin.entity.UnifiedOrderRequestEntity;
import com.fenrir.pay.weixin.entity.UnifiedOrderResponseEntity;
import com.fenrir.pay.weixin.sdk.WeixinPaySdk;
import com.fenrir.pay.weixin.sdk.WeixinPayUtil;

/**
 * 微信支付的api实现
 * @author fenrir
 *
 */
public class WeixinPayApi {

	/**
	 * 微信支付商户配置
	 */
	private WeixinPaySdk weixinPaySdk;
	
	public WeixinPayApi(WeixinPayApiConfig weixinPayApiConfig, WeixinPayMerchantConfig weixinPayMerchantConfig) {
		super();
		this.weixinPaySdk = new WeixinPaySdk(weixinPayApiConfig, weixinPayMerchantConfig);
	}
	
	/**
	 * 统一下单
	 * @param request
	 * @return
	 */
	public UnifiedOrderResponseEntity unifiedOrder(UnifiedOrderRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WeixinPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = null;

		if (WeixinPaySdkConfig.JSAPI.equals(request.getTrade_type())) {
			responseData = weixinPaySdk.unifiedOrderJsapi(requestData);
		} else {
			responseData = weixinPaySdk.unifiedOrder(requestData);
		}

		// 响应转实体
		return WeixinPayUtil.mapToEntity(UnifiedOrderResponseEntity.class, responseData);
	}

	/**
	 * 查询订单
	 * @param transactionId 微信订单号，微信订单号和商户订单号二选一必填，建议优先使用微信的订单号
	 * @param outTradeNo 商户订单号，微信订单号和商户订单号二选一必填，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
	 * @return
	 * @throws Exception
	 */
	public QueryOrderResponseEntity queryOrder(String transactionId, String outTradeNo) throws Exception {
		// 调用API
		Map<String, String> responseData = weixinPaySdk.queryOrder(transactionId, outTradeNo);

		// 响应转实体
		return WeixinPayUtil.mapToEntity(QueryOrderResponseEntity.class, responseData);
	}

	/**
	 * 关闭订单
	 * @param outTradeNo 订单号
	 * @return
	 */
	public CloseOrderResponseEntity revocationOrder(String outTradeNo) throws Exception {
		// 调用API
		Map<String, String> responseData = weixinPaySdk.revocationOrder(outTradeNo);

		// 响应转实体
		return WeixinPayUtil.mapToEntity(CloseOrderResponseEntity.class, responseData);
	}

	/**
	 * 申请退款
	 * @param request
	 * @return
	 */
	public RefundResponseEntity refund(RefundRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WeixinPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = weixinPaySdk.refund(requestData);

		// 响应转实体
		return WeixinPayUtil.mapToEntity(RefundResponseEntity.class, responseData);
	}

	/**
	 * 查询退款
	 * @param request
	 * @return
	 */
	public RefundQueryResponseEntity refundQuery(RefundQueryRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WeixinPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = weixinPaySdk.refundQuery(requestData);

		// 响应转实体
		return WeixinPayUtil.mapToEntity(RefundQueryResponseEntity.class, responseData);
	}

}