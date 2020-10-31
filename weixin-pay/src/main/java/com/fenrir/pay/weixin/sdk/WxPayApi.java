package com.fenrir.pay.weixin.sdk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fenrir.pay.weixin.config.WeixinPaySdkConfig;
import com.wx.config.WxMerchantConfig;
import com.wx.config.WxSystemConfig;

/**
 * 微信支付API
 * @author yzm
 *
 */
public class WxPayApi {

	/**
	 * 统一下单
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> unifiedOrder(Map<String, String> requestData) throws Exception {

		String url;

		if (WxSystemConfig.USE_SAND_BOX) {
			url = WeixinPaySdkConfig.SANDBOX_UNIFIEDORDER_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.UNIFIEDORDER_URL_SUFFIX;
		}

		if (WeixinPaySdkConfig.FIELD_NOTIFY_URL != null) {
			requestData.put(WeixinPaySdkConfig.FIELD_NOTIFY_URL, WxSystemConfig.NOTIFY_PAY_URL);
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 统一下单-JSAPI
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> unifiedOrderJsapi(Map<String, String> requestData) throws Exception {
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

			sb.append("key=").append(WxMerchantConfig.MCH_KEY);

			response.put(WeixinPaySdkConfig.FIELD_SIGN, WxPayUtil.MD5(sb.toString()).toUpperCase());

			response.put("time_stamp", time);
		}
		
		return response;
	}

	/**
	 * 查询订单
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> queryOrder(Map<String, String> requestData) throws Exception {
		String url;

		if (WxSystemConfig.USE_SAND_BOX) {
			url = WeixinPaySdkConfig.SANDBOX_ORDERQUERY_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.ORDERQUERY_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 关闭订单
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> revocationOrder(Map<String, String> requestData) throws Exception {
		String url;

		if (WxSystemConfig.USE_SAND_BOX) {
			url = WeixinPaySdkConfig.SANDBOX_CLOSEORDER_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.CLOSEORDER_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 申请退款
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> refund(Map<String, String> requestData) throws Exception {
		String url;

		if (WxSystemConfig.USE_SAND_BOX) {
			url = WeixinPaySdkConfig.SANDBOX_REFUND_URL_SUFFIX;
		} else {
			url = WeixinPaySdkConfig.REFUND_URL_SUFFIX;
		}

		String respXml = requestWithoutCert(url, fillRequestData(requestData));

		return processResponseXml(respXml);
	}

	/**
	 * 查询退款
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> refundQuery(Map<String, String> requestData) throws Exception {
		String url;

		if (WxSystemConfig.USE_SAND_BOX) {
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
	 * @param urlSuffix String
	 * @param reqData 向wxpay post的请求数据
	 * @return API返回数据
	 * @throws Exception
	 */
	public static String requestWithoutCert(String urlSuffix, Map<String, String> reqData) throws Exception {
		
		String uuid = reqData.get(WeixinPaySdkConfig.FIELD_NONCE_STR);
		
		String reqBody = WxPayUtil.mapToXml(reqData);

		String resp = WxPayRequest.requestWithoutCert(
			urlSuffix, 
			uuid, 
			reqBody
		);
		
		return resp;
	}

    /**
     * 需要证书的请求
     * @param urlSuffix String
     * @param reqData 向wxpay post的请求数据  Map
     * @return API返回数据
     * @throws Exception
     */
    public String requestWithCert(String urlSuffix, Map<String, String> reqData) throws Exception {
        String uuid = reqData.get(WeixinPaySdkConfig.FIELD_NONCE_STR);

        String reqBody = WxPayUtil.mapToXml(reqData);

        String resp = WxPayRequest.requestWithCert(
            urlSuffix,
            uuid,
            reqBody
        );

        return resp;
    }

    /**
	 * 处理 HTTPS API返回数据,转换成Map对象.return_code为SUCCESS时,验证签名.
	 * 
	 * @param xmlStr API返回的XML格式数据
	 * @return Map类型数据
	 * @throws Exception
	 */
	public static Map<String, String> processResponseXml(String xmlStr) throws Exception {
		String return_code;

		Map<String, String> respData = WxPayUtil.xmlToMap(xmlStr);

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
	 * 向 Map 中添加 appid,mch_id,nonce_str,sign_type,sign
	 * 该函数适用于商户适用于统一下单等接口,不适用于红包,代金券接口
	 *
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> fillRequestData(Map<String, String> reqData) throws Exception {
//		if (!reqData.containsKey(WeixinPaySdkConfig.FIELD_APPID)) {
//			reqData.put(WeixinPaySdkConfig.FIELD_APPID, MerchantConfig.SHOP_APP_ID);
//		}

		reqData.put(WeixinPaySdkConfig.FIELD_APPID, WxMerchantConfig.SHOP_APP_ID);

		reqData.put(WeixinPaySdkConfig.FIELD_MCH_ID, WxMerchantConfig.MCH_ID);

		reqData.put(WeixinPaySdkConfig.FIELD_NONCE_STR, WxPayUtil.generateNonceStr());

		reqData.put(WeixinPaySdkConfig.FIELD_SIGN_TYPE, WxSystemConfig.ENCRYPTION_METHOD);

		reqData.put(WeixinPaySdkConfig.FIELD_SIGN, WxPayUtil.generateSignature(reqData, WxMerchantConfig.MCH_KEY, WxSystemConfig.ENCRYPTION_METHOD));

		return reqData;
	}

	/**
	 * 判断XML数据的sign是否有效,必须包含sign字段,否则返回false.
	 *
	 * @param reqData 向wxpay post的请求数据
	 * @return 签名是否有效
	 * @throws Exception
	 */
	public static boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
		// 返回数据的签名方式和请求中给定的签名方式是一致的
		return WxPayUtil.isSignatureValid(reqData, WxMerchantConfig.MCH_KEY, WxSystemConfig.ENCRYPTION_METHOD);
	}
	
}