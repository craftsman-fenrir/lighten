package com.fenrir.pay.weixin.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置
 * @author fenrir
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeixinApiConfig {
	
	/**
	 * 是否使用沙箱环境
	 */
	private boolean useSandBox;

	/**
	 * 加密方式
	 */
	private String encryptionMethod;
	
	/**
	 * 支付通知地址
	 */
	private String notifyPayUrl;

	/**
	 * 退款通知地址
	 */
	private String notifyRefundUrl;

	/**
	 * HTTP(S) 连接超时时间，单位毫秒
	 */
	private int httpConnectTimeoutMs;

	/**
	 * HTTP(S) 读数据超时时间，单位毫秒
	 */
	private int httpReadTimeoutMs;

	/**
	 * 是否自动上报. 若要关闭自动上报，子类中实现该函数返回 false 即可.
	 */
	private boolean isAutoReport;

	/**
	 * 进行健康上报的线程的数量
	 */
	private int reportWorkerNum;

	/**
	 * 健康上报缓存消息的最大数量
	 * 会有线程去独立上报 粗略计算：加入一条消息200B，10000消息占用空间2000KB，约为2MB，可以接受
	 */
	private int reportQueueMaxSize;

	/**
	 * 批量上报，一次最多上报多个数据
	 */
	private int reportBatchSize;
	
}