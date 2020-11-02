package com.fenrir.pay.weixin.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.fenrir.pay.weixin.config.WeixinPayApiConfig;
import com.fenrir.pay.weixin.config.WeixinPayMerchantConfig;
import com.fenrir.pay.weixin.config.WeixinPaySdkConfig;

/**
 * 微信支付http请求的模板
 * @author fenrir
 *
 */
public class WeixinPayHttpTemplate {

	/**
	 * 微信支付api配置
	 */
	private WeixinPayApiConfig weixinPayApiConfig;
	
	/**
	 * 微信支付商户配置
	 */
	private WeixinPayMerchantConfig weixinPayMerchantConfig;
	
	public WeixinPayHttpTemplate(WeixinPayApiConfig weixinPayApiConfig, WeixinPayMerchantConfig weixinPayMerchantConfig) {
		super();
		this.weixinPayApiConfig = weixinPayApiConfig;
		this.weixinPayMerchantConfig = weixinPayMerchantConfig;
	}

	/**
	 * 请求,只请求一次,不做重试
	 * 
	 * @param domain
	 * @param urlSuffix
	 * @param data
	 * @param connectTimeoutMs
	 * @param readTimeoutMs
	 * @param useCert 是否使用证书,针对退款,撤销等操作
	 * @return
	 * @throws Exception
	 */
	private String requestOnce(String domain, String urlSuffix, String data, int connectTimeoutMs,
			int readTimeoutMs, boolean useCert) throws Exception {
		BasicHttpClientConnectionManager connManager;

		if (useCert) {
			// 证书
			char[] password = weixinPayMerchantConfig.getMchId().toCharArray();

			File file = new File(weixinPayMerchantConfig.getCertStream());   
			InputStream certStream = new FileInputStream(file);

			KeyStore ks = KeyStore.getInstance(weixinPayMerchantConfig.getCertFormat());

			ks.load(certStream, password);

			// 实例化密钥库 & 初始化密钥工厂
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

			kmf.init(ks, password);

			// 创建 SSLContext
			SSLContext sslContext = SSLContext.getInstance(WeixinPaySdkConfig.TLS);

			sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
				sslContext,
				new String[] {WeixinPaySdkConfig.TLS_V1}, 
				null, 
				new DefaultHostnameVerifier()
			);

			connManager = new BasicHttpClientConnectionManager(
				RegistryBuilder.<ConnectionSocketFactory> create()
					.register(WeixinPaySdkConfig.HTTP, PlainConnectionSocketFactory.getSocketFactory())
					.register(WeixinPaySdkConfig.HTTPS, sslConnectionSocketFactory)
					.build(), 
				null, 
				null, 
				null
			);
		} else {
			connManager = new BasicHttpClientConnectionManager(
				RegistryBuilder.<ConnectionSocketFactory> create()
					.register(WeixinPaySdkConfig.HTTP, PlainConnectionSocketFactory.getSocketFactory())
					.register(WeixinPaySdkConfig.HTTPS, SSLConnectionSocketFactory.getSocketFactory())
					.build(),
				null, 
				null, 
				null
			);
		}

		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(connManager)
				.build();

		String url = WeixinPaySdkConfig.HTTPS_PREFIX + domain + urlSuffix;
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(readTimeoutMs)
				.setConnectTimeout(connectTimeoutMs)
				.build();
		
		httpPost.setConfig(requestConfig);

		StringEntity postEntity = new StringEntity(data, WeixinPaySdkConfig.UTF_8);
		httpPost.addHeader(WeixinPaySdkConfig.CONTENT_TYPE_KEY, WeixinPaySdkConfig.CONTENT_TYPE_VALUE);
		httpPost.addHeader(WeixinPaySdkConfig.USER_AGENT_KEY, WeixinPaySdkConfig.USER_AGENT + " " + weixinPayMerchantConfig.getMchId());
		httpPost.setEntity(postEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		return EntityUtils.toString(httpEntity, WeixinPaySdkConfig.UTF_8);
	}
	
	/**
	 * HTTP请求
	 * @param urlSuffix
	 * @param uuid
	 * @param data
	 * @param connectTimeoutMs 超时时间,单位是毫秒
	 * @param readTimeoutMs 超时时间,单位是毫秒
	 * @param useCert
	 * @return
	 * @throws Exception
	 */
	private String request(
		String urlSuffix, String uuid, String data, int connectTimeoutMs, int readTimeoutMs, boolean useCert
	) throws Exception {
		Exception exception = null;

		long elapsedTimeMillis = 0;

		long startTimestampMs = System.currentTimeMillis();

		boolean firstHasDnsErr = false;

		boolean firstHasConnectTimeout = false;

		boolean firstHasReadTimeout = false;

		try {
			String result = requestOnce(WeixinPaySdkConfig.DOMAIN_API, urlSuffix, data, connectTimeoutMs, readTimeoutMs,
					useCert);
			elapsedTimeMillis = System.currentTimeMillis() - startTimestampMs;

			// 上报网络状态
			//config.getWXPayDomain().report(DOMAIN, elapsedTimeMillis, exception);

			WeixinPayReport.getInstance(weixinPayApiConfig, weixinPayMerchantConfig).report(
				uuid, 
				elapsedTimeMillis, 
				WeixinPaySdkConfig.DOMAIN_API, 
				WeixinPaySdkConfig.IS_PRIMARY_DOMAIN,
				connectTimeoutMs, 
				readTimeoutMs, 
				firstHasDnsErr, 
				firstHasConnectTimeout, 
				firstHasReadTimeout
			);

			return result;
		} catch (UnknownHostException ex) { // dns 解析错误,或域名不存在
			exception = ex;

			firstHasDnsErr = true;

			elapsedTimeMillis = System.currentTimeMillis() - startTimestampMs;

			/*WxPayUtil.getLogger().warn("UnknownHostException for domainInfo {}", DOMAIN, IS_PRIMARY_DOMAIN);*/

			WeixinPayReport.getInstance(weixinPayApiConfig, weixinPayMerchantConfig).report(
				uuid,
				elapsedTimeMillis,
				WeixinPaySdkConfig.DOMAIN_API,
				WeixinPaySdkConfig.IS_PRIMARY_DOMAIN,
				connectTimeoutMs,
				readTimeoutMs,
				firstHasDnsErr,
				firstHasConnectTimeout,
				firstHasReadTimeout
			);
		} catch (ConnectTimeoutException ex) {
			exception = ex;

			firstHasConnectTimeout = true;

			elapsedTimeMillis = WeixinPayUtil.getCurrentTimestampMs() - startTimestampMs;

			/*WxPayUtil.getLogger().warn("connect timeout happened for domainInfo {}", DOMAIN, IS_PRIMARY_DOMAIN);*/

			WeixinPayReport.getInstance(weixinPayApiConfig, weixinPayMerchantConfig).report(
				uuid, 
				elapsedTimeMillis, 
				WeixinPaySdkConfig.DOMAIN_API, 
				WeixinPaySdkConfig.IS_PRIMARY_DOMAIN,
				connectTimeoutMs, 
				readTimeoutMs, 
				firstHasDnsErr, 
				firstHasConnectTimeout, 
				firstHasReadTimeout
			);
		} catch (SocketTimeoutException ex) {
			exception = ex;

			firstHasReadTimeout = true;

			elapsedTimeMillis = WeixinPayUtil.getCurrentTimestampMs() - startTimestampMs;

			/*WxPayUtil.getLogger().warn("timeout happened for domainInfo {}", DOMAIN, IS_PRIMARY_DOMAIN);*/

			WeixinPayReport.getInstance(weixinPayApiConfig, weixinPayMerchantConfig).report(
				uuid, 
				elapsedTimeMillis, 
				WeixinPaySdkConfig.DOMAIN_API, 
				WeixinPaySdkConfig.IS_PRIMARY_DOMAIN,
				connectTimeoutMs, 
				readTimeoutMs, 
				firstHasDnsErr, 
				firstHasConnectTimeout, 
				firstHasReadTimeout
			);
		} catch (Exception ex) {
			exception = ex;

			elapsedTimeMillis = WeixinPayUtil.getCurrentTimestampMs() - startTimestampMs;

			WeixinPayReport.getInstance(weixinPayApiConfig, weixinPayMerchantConfig).report(
				uuid, 
				elapsedTimeMillis, 
				WeixinPaySdkConfig.DOMAIN_API, 
				WeixinPaySdkConfig.IS_PRIMARY_DOMAIN,
				connectTimeoutMs, 
				readTimeoutMs, 
				firstHasDnsErr, 
				firstHasConnectTimeout, 
				firstHasReadTimeout
			);
		}

		// 上报网络状态
		//config.getWXPayDomain().report(DOMAIN, elapsedTimeMillis, exception);

		throw exception;
	}
	
	/**
	 * 可重试的,非双向认证的请求
	 * 
	 * @param urlSuffix
	 * @param uuid
	 * @param data
	 * @return
	 */
	public String requestWithoutCert(String urlSuffix, String uuid, String data) throws Exception {
		return request(
			urlSuffix, 
			uuid, 
			data, 
			weixinPayApiConfig.getHttpConnectTimeoutMs(), 
			weixinPayApiConfig.getHttpReadTimeoutMs(), 
			false
		);
	}

	/**
	 * 可重试的,双向认证的请求
	 * 
	 * @param urlSuffix
	 * @param uuid
	 * @param data
	 * @return
	 */
	public String requestWithCert(String urlSuffix, String uuid, String data) throws Exception {
		return request(
			urlSuffix, 
			uuid, 
			data, 
			weixinPayApiConfig.getHttpConnectTimeoutMs(), 
			weixinPayApiConfig.getHttpReadTimeoutMs(), 
			true
		);
	}
	
}