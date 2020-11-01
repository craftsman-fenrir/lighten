package com.fenrir.pay.weixin.sdk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fenrir.pay.weixin.config.WeixinPayApiConfig;
import com.fenrir.pay.weixin.config.WeixinPayMerchantConfig;
import com.fenrir.pay.weixin.config.WeixinPaySdkConfig;

/**
 * 微信支付sdk，基础的使用方式
 * @author fenrir
 *
 */
public class WeixinPaySdk {

	/**
	 * 微信支付api配置
	 */
	private WeixinPayApiConfig weixinPayApiConfig;
	
	/**
	 * 微信支付商户配置
	 */
	private WeixinPayMerchantConfig weixinPayMerchantConfig;
	
	/**
	 * 微信支付商户配置
	 */
	private WeixinPayHttpTemplate weixinPayHttpTemplate;
	
	public WeixinPaySdk(WeixinPayApiConfig weixinPayApiConfig, WeixinPayMerchantConfig weixinPayMerchantConfig) {
		super();
		this.weixinPayApiConfig = weixinPayApiConfig;
		this.weixinPayMerchantConfig = weixinPayMerchantConfig;
		this.weixinPayHttpTemplate = new WeixinPayHttpTemplate(weixinPayApiConfig, weixinPayMerchantConfig);
	}

	/**
	 * 统一下单
	 * @param requestData 微信支付请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> unifiedOrder(Map<String, String> requestData) throws Exception {
		String url;

		if (weixinPayApiConfig.isUseSandBox()) {
			url = WeixinPaySdkConfig.SANDBOX_UNIFIEDORDER_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.UNIFIEDORDER_URL_SUFFIX;
		}

		if (WeixinPaySdkConfig.FIELD_NOTIFY_URL != null) {
			requestData.put(WeixinPaySdkConfig.FIELD_NOTIFY_URL, weixinPayApiConfig.getNotifyPayUrl());
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 统一下单-JSAPI
	 * @param requestData 微信支付api请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> unifiedOrderJsapi(Map<String, String> requestData) throws Exception {
		// 获取统一下单返回值
		Map<String, String> response = unifiedOrder(requestData);
		
		// 第二次签名
		if (
			response.containsKey(WeixinPaySdkConfig.FIELD_RETURN_CODE) &&
			response.get(WeixinPaySdkConfig.FIELD_RETURN_CODE).equals(WeixinPaySdkConfig.SUCCESS)
		) {
			Map<String, String> againMap = new HashMap<String, String>();

			String time = String.valueOf(System.currentTimeMillis() / 1000);

			againMap.put("appId", response.get(WeixinPaySdkConfig.FIELD_APPID));

			againMap.put("nonceStr", response.get("nonce_str"));

			againMap.put("package", "prepay_id=" + response.get("prepay_id"));

			againMap.put("signType", WeixinPaySdkConfig.MD5);

			againMap.put("timeStamp", time);

			Set<String> keySet = againMap.keySet();

			String[] keyArray = keySet.toArray(new String[keySet.size()]);

			Arrays.sort(keyArray);

			StringBuilder sb = new StringBuilder();

			for (String k : keyArray) {
				// 参数值为空，则不参与签名
				if (null != againMap.get(k) && againMap.get(k).trim().length() > 0) {
					sb.append(k).append("=").append(againMap.get(k)).append("&");
				}
			}

			sb.append("key=").append(weixinPayMerchantConfig.getMchKey());

			response.put(WeixinPaySdkConfig.FIELD_SIGN, WeixinPayUtil.MD5(sb.toString()).toUpperCase());

			response.put("time_stamp", time);
		}
		
		return response;
	}

	/**
	 * 查询订单
	 * @param requestData 微信支付请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryOrder(Map<String, String> requestData) throws Exception {
		String url;

		if (weixinPayApiConfig.isUseSandBox()) {
			url = WeixinPaySdkConfig.SANDBOX_ORDERQUERY_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.ORDERQUERY_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 关闭订单
	 * @param requestData 微信支付请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> revocationOrder(Map<String, String> requestData) throws Exception {
		String url;

		if (weixinPayApiConfig.isUseSandBox()) {
			url = WeixinPaySdkConfig.SANDBOX_CLOSEORDER_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.CLOSEORDER_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 申请退款
	 * @param requestData 微信支付请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> refund(Map<String, String> requestData) throws Exception {
		String url;

		if (weixinPayApiConfig.isUseSandBox()) {
			url = WeixinPaySdkConfig.SANDBOX_REFUND_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.REFUND_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 查询退款
	 * @param requestData 微信支付请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> refundQuery(Map<String, String> requestData) throws Exception {
		String url;

		if (weixinPayApiConfig.isUseSandBox()) {
			url = WeixinPaySdkConfig.SANDBOX_REFUNDQUERY_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.REFUNDQUERY_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 不需要证书的请求
	 * 
	 * @param urlSuffix url后缀
	 * @param reqData 微信支付api请求参数
	 * @return API返回数据
	 * @throws Exception
	 */
	public String requestWithoutCert(String urlSuffix, Map<String, String> reqData) throws Exception {
		
		String uuid = reqData.get(WeixinPaySdkConfig.FIELD_NONCE_STR);
		
		String reqBody = WeixinPayUtil.mapToXml(reqData);

		return weixinPayHttpTemplate.requestWithoutCert(urlSuffix, uuid, reqBody);
	}

    /**
     * 需要证书的请求
     * @param urlSuffix url后缀
     * @param reqData 微信支付api请求参数
     * @return API返回数据
     * @throws Exception
     */
    public String requestWithCert(String urlSuffix, Map<String, String> reqData) throws Exception {
        String uuid = reqData.get(WeixinPaySdkConfig.FIELD_NONCE_STR);

        String reqBody = WeixinPayUtil.mapToXml(reqData);

        return weixinPayHttpTemplate.requestWithCert(urlSuffix, uuid, reqBody);
    }

    /**
     * 处理 HTTPS API返回数据，转换成map对象.return_code为SUCCESS时，验证签名
     * @param xmlStr api返回的xml数据
     * @return 转换成map格式的xmlStr
     * @throws Exception
     */
	public Map<String, String> processResponseXml(String xmlStr) throws Exception {
		String return_code;

		Map<String, String> respData = WeixinPayUtil.xmlToMap(xmlStr);

		if (respData.containsKey(WeixinPaySdkConfig.FIELD_RETURN_CODE)) {
			return_code = respData.get(WeixinPaySdkConfig.FIELD_RETURN_CODE);
		} else {
			throw new Exception(String.format("No `return_code` in XML: %s", xmlStr));
		}

		if (return_code.equals(WeixinPaySdkConfig.FAIL)) {
			return respData;
		} else if (return_code.equals(WeixinPaySdkConfig.SUCCESS)) {
			if (isResponseSignatureValid(respData)) {
				return respData;
			} else {
				throw new Exception(String.format("Invalid sign value in XML: %s", xmlStr));
			}
		} else {
			throw new Exception(String.format("return_code value %s is invalid in XML: %s", return_code, xmlStr));
		}
	}
	
	/**
	 * 调用微信支付api前，补全请求参数
	 * 该函数适用于商户适用于统一下单等接口，不适用于红包，代金券接口
	 * @param reqData 微信支付请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> fillRequestData(Map<String, String> reqData) throws Exception {
//		if (!reqData.containsKey(WeixinPaySdkConfig.FIELD_APPID)) {
//			reqData.put(WeixinPaySdkConfig.FIELD_APPID, MerchantConfig.SHOP_APP_ID);
//		}

		reqData.put(WeixinPaySdkConfig.FIELD_APPID, weixinPayMerchantConfig.getAppid());

		reqData.put(WeixinPaySdkConfig.FIELD_MCH_ID, weixinPayMerchantConfig.getMchId());

		reqData.put(WeixinPaySdkConfig.FIELD_NONCE_STR, WeixinPayUtil.generateNonceStr());

		reqData.put(WeixinPaySdkConfig.FIELD_SIGN_TYPE, weixinPayApiConfig.getEncryptionMethod());

		reqData.put(WeixinPaySdkConfig.FIELD_SIGN, WeixinPayUtil.generateSignature(
			reqData, weixinPayMerchantConfig.getMchKey(), weixinPayApiConfig.getEncryptionMethod())
		);

		return reqData;
	}

	/**
	 * 判断XML数据的sign是否有效，必须包含sign字段，否则返回false
	 * @param reqData 微信支付请求参数
	 * @return 签名是否有效
	 * @throws Exception
	 */
	public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
		// 返回数据的签名方式和请求中给定的签名方式是一致的
		return WeixinPayUtil.isSignatureValid(weixinPayApiConfig, weixinPayMerchantConfig, reqData);
	}
	
}