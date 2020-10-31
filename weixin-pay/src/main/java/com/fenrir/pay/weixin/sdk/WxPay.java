package com.fenrir.pay.weixin.sdk;

import java.util.Map;

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

/**
 * 微信支付
 * @author yzm
 *
 */
public class WxPay {

	/**
	 * 统一下单
	 * @param request
	 * @return
	 */
	public static UnifiedOrderResponseEntity unifiedOrder(UnifiedOrderRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = null;

		if (WeixinPaySdkConfig.JSAPI.equals(request.getTrade_type())) {
			responseData = WxPayApi.unifiedOrderJsapi(requestData);
		} else {
			responseData = WxPayApi.unifiedOrder(requestData);
		}

		// 响应转实体
		return WxPayUtil.mapToEntity(UnifiedOrderResponseEntity.class, responseData);
	}

	/**
	 * 订单查询
	 * @param request
	 * @return
	 */
	public static QueryOrderResponseEntity queryOrder(QueryOrderRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = WxPayApi.queryOrder(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(QueryOrderResponseEntity.class, responseData);
	}

	/**
	 * 关闭订单
	 * @param request
	 * @return
	 */
	public static CloseOrderResponseEntity revocationOrder(CloseOrderRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = WxPayApi.revocationOrder(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(CloseOrderResponseEntity.class, responseData);
	}

	/**
	 * 申请退款
	 * @param request
	 * @return
	 */
	public static RefundResponseEntity refund(RefundRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = WxPayApi.refund(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(RefundResponseEntity.class, responseData);
	}

	/**
	 * 查询退款
	 * @param request
	 * @return
	 */
	public static RefundQueryResponseEntity refundQuery(RefundQueryRequestEntity request) throws Exception {
		// 请求转map
		Map<String, String> requestData = WxPayUtil.entityToMap(request);

		// 调用API
		Map<String, String> responseData = WxPayApi.refundQuery(requestData);

		// 响应转实体
		return WxPayUtil.mapToEntity(RefundQueryResponseEntity.class, responseData);
	}

}