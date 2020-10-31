package com.fenrir.pay.weixin;

import com.fenrir.pay.weixin.config.WeixinApiConfig;
import com.fenrir.pay.weixin.config.WeixinPayMerchantConfig;

import lombok.Data;

/**
 * 微信支付的api实现
 * 需要把微信商户的参数填进来实例化再使用
 * @author fenrir
 *
 */
@Data
public class WeixinPayApi {

	/**
	 * 微信支付api配置
	 */
	private WeixinApiConfig weixinApiConfig;
	
	/**
	 * 微信支付商户配置
	 */
	private WeixinPayMerchantConfig weixinPayMerchantConfig;

	public WeixinPayApi(WeixinApiConfig weixinApiConfig, WeixinPayMerchantConfig weixinPayMerchantConfig) {
		super();
		this.weixinApiConfig = weixinApiConfig;
		this.weixinPayMerchantConfig = weixinPayMerchantConfig;
	}

}