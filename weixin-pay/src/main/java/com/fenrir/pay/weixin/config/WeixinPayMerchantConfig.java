package com.fenrir.pay.weixin.config;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信商户配置
 * @author fenrir
 *
 */
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
	private InputStream certStream;

	/**
	 * 商户证书格式
	 * [PKCS12, PEM, CER, JKS, ]
	 */
	private String certFormat;

}