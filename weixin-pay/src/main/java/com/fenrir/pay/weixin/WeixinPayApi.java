package com.fenrir.pay.weixin;

import java.util.Map;

import com.fenrir.pay.weixin.config.WeixinPayApiConfig;
import com.fenrir.pay.weixin.config.WeixinPayMerchantConfig;
import com.fenrir.pay.weixin.config.WeixinPaySdkConfig;
import com.fenrir.pay.weixin.entity.CloseOrderRequestEntity;
import com.fenrir.pay.weixin.entity.CloseOrderResponseEntity;
import com.fenrir.pay.weixin.entity.QueryOrderRequestEntity;
import com.fenrir.pay.weixin.entity.QueryOrderResponseEntity;
import com.fenrir.pay.weixin.entity.RefundQueryRequestEntity;
import com.fenrir.pay.weixin.entity.RefundQueryResponseEntity;
import com.fenrir.pay.weixin.entity.RefundRequestEntity;
import com.fenrir.pay.weixin.entity.RefundResponseEntity;
import com.fenrir.pay.weixin.entity.UnifiedOrderRequestEntity;
import com.fenrir.pay.weixin.entity.UnifiedOrderResponseEntity;
import com.fenrir.pay.weixin.sdk.WeixinPaySdk;
import com.fenrir.pay.weixin.sdk.WxPayUtil;

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
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = null;

		if (WeixinPaySdkConfig.JSAPI.equals(request.getTrade_type())) {
			responseData = weixinPaySdk.unifiedOrderJsapi(requestData);
		} else {
			responseData = weixinPaySdk.unifiedOrder(requestData);
		}

		// 响应转实体
		return WxPayUtil.mapToEntity(UnifiedOrderResponseEntity.class, responseData);
	}

	/**
	 * 订单查询
	 * @param request
	 * @return
	 */
	public QueryOrderResponseEntity queryOrder(QueryOrderRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = weixinPaySdk.queryOrder(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(QueryOrderResponseEntity.class, responseData);
	}

	/**
	 * 关闭订单
	 * @param request
	 * @return
	 */
	public CloseOrderResponseEntity revocationOrder(CloseOrderRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = weixinPaySdk.revocationOrder(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(CloseOrderResponseEntity.class, responseData);
	}

	/**
	 * 申请退款
	 * @param request
	 * @return
	 */
	public RefundResponseEntity refund(RefundRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = weixinPaySdk.refund(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(RefundResponseEntity.class, responseData);
	}

	/**
	 * 查询退款
	 * @param request
	 * @return
	 */
	public RefundQueryResponseEntity refundQuery(RefundQueryRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = weixinPaySdk.refundQuery(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(RefundQueryResponseEntity.class, responseData);
	}

}