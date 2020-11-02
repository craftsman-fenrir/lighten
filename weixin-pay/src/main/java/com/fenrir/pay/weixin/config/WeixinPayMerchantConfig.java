package com.fenrir.pay.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信商户配置
 * @author fenrir
 *
 */
@ConfigurationProperties(prefix = "fenrir.pay.weixin.merchant")
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeixinPayMerchantConfig {

	/**
	 * appid
	 */
	private String appid;

	/**
	 * 商户id
	 */
	private String mchId;
	
	/**
	 * 商户密钥
	 */
	private String mchKey;
	
	/**
	 * 商户证书
	 */
	private String certStream;

	/**
	 * 商户证书格式
	 * [PKCS12, PEM, CER, JKS, ]
	 */
	private String certFormat;

}