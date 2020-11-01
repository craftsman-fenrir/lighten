package com.fenrir.pay.weixin.sdk;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fenrir.pay.weixin.config.WeixinPayApiConfig;
import com.fenrir.pay.weixin.config.WeixinPayMerchantConfig;
import com.fenrir.pay.weixin.config.WeixinPaySdkConfig;

/**
 * 微信支付工具
 * @author yzm
 *
 */
public class WxPayUtil {

	private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final Random RANDOM = new SecureRandom();

	/**
	 * XML格式字符串转换为Map
	 *
	 * @param strXML XML字符串
	 * @return XML数据转换后的Map
	 * @throws Exception
	 */
	public static Map<String, String> xmlToMap(String strXML) throws Exception {
		try {
			Map<String, String> data = new HashMap<String, String>();

			DocumentBuilder documentBuilder = WxPayXmlUtil.newDocumentBuilder();

			InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));

			org.w3c.dom.Document doc = documentBuilder.parse(stream);

			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getDocumentElement().getChildNodes();

			for (int idx = 0; idx < nodeList.getLength(); ++idx) {
				Node node = nodeList.item(idx);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element element = (org.w3c.dom.Element) node;
					data.put(element.getNodeName(), element.getTextContent());
				}
			}

			try {
				stream.close();
			} catch (Exception ex) {
				// do nothing
			}

			return data;
		} catch (Exception ex) {
			/*WxPayUtil.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}",
					ex.getMessage(), strXML);*/
			throw ex;
		}
	}

	/**
	 * 将Map转换为XML格式的字符串
	 *
	 * @param data Map类型数据
	 * @return XML格式的字符串
	 * @throws Exception
	 */
	public static String mapToXml(Map<String, String> data) throws Exception {
		org.w3c.dom.Document document = WxPayXmlUtil.newDocument();

		org.w3c.dom.Element root = document.createElement("xml");

		document.appendChild(root);

		for (String key : data.keySet()) {
			String value = data.get(key);

			if (value == null) {
				value = "";
			}

			value = value.trim();

			org.w3c.dom.Element filed = document.createElement(key);

			filed.appendChild(document.createTextNode(value));

			root.appendChild(filed);
		}

		TransformerFactory tf = TransformerFactory.newInstance();

		Transformer transformer = tf.newTransformer();

		DOMSource source = new DOMSource(document);

		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter writer = new StringWriter();

		StreamResult result = new StreamResult(writer);

		transformer.transform(source, result);

		String output = writer.getBuffer().toString(); // .replaceAll("\n|\r",
		// "");

		try {
			writer.close();
		} catch (Exception ex) {
		}

		return output;
	}

	/**
	 * 生成带有 sign 的 XML 格式字符串
	 *
	 * @param data Map类型数据
	 * @param key API密钥
	 * @return 含有sign字段的XML
	 */
	public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
		return generateSignedXml(data, key, WeixinPaySdkConfig.MD5);
	}

	/**
	 * 生成带有 sign 的 XML 格式字符串
	 *
	 * @param data Map类型数据
	 * @param key API密钥
	 * @param signType 签名类型
	 * @return 含有sign字段的XML
	 */
	public static String generateSignedXml(final Map<String, String> data, String key, String signType)
			throws Exception {
		String sign = generateSignature(data, key, signType);

		data.put(WeixinPaySdkConfig.FIELD_SIGN, sign);

		return mapToXml(data);
	}

	/**
	 * 判断签名是否正确
	 *
	 * @param xmlStr XML格式数据
	 * @param key API密钥
	 * @return 签名是否正确
	 * @throws Exception
	 */
	public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
		Map<String, String> data = xmlToMap(xmlStr);

		if (!data.containsKey(WeixinPaySdkConfig.FIELD_SIGN)) {
			return false;
		}

		String sign = data.get(WeixinPaySdkConfig.FIELD_SIGN);

		return generateSignature(data, key).equals(sign);
	}

	/**
	 * 判断签名是否正确，必须包含sign字段，否则返回false
	 * @param weixinPayApiConfig 微信支付api配置
	 * @param weixinPayMerchantConfig 微信支付商户配置
	 * @param data 微信支付请求参数
	 * @return 签名是否正确
	 * @throws Exception
	 */
	public static boolean isSignatureValid(
		WeixinPayApiConfig weixinPayApiConfig, WeixinPayMerchantConfig weixinPayMerchantConfig, Map<String, String> data
	) throws Exception {
		if (!data.containsKey(WeixinPaySdkConfig.FIELD_SIGN)) {
			return false;
		}

		String sign = data.get(WeixinPaySdkConfig.FIELD_SIGN);

		return generateSignature(data, weixinPayMerchantConfig.getMchKey(), weixinPayApiConfig.getEncryptionMethod()).equals(sign);
	}

	/**
	 * 生成签名
	 *
	 * @param data 待签名数据
	 * @param key API密钥
	 * @return 签名
	 */
	public static String generateSignature(final Map<String, String> data, String key) throws Exception {
		return generateSignature(data, key, WeixinPaySdkConfig.MD5);
	}

	/**
	 * 生成签名
	 * 注意，若含有sign_type字段，必须和signType参数保持一致
	 *
	 * @param data 待签名数据
	 * @param key API密钥
	 * @param signType 签名方式
	 * @return 签名
	 */
	public static String generateSignature(final Map<String, String> data, String key, String signType)
			throws Exception {
		Set<String> keySet = data.keySet();

		String[] keyArray = keySet.toArray(new String[keySet.size()]);

		Arrays.sort(keyArray);

		StringBuilder sb = new StringBuilder();

		for (String k : keyArray) {
			if (k.equals(WeixinPaySdkConfig.FIELD_SIGN)) {
				continue;
			}

			// 参数值为空,则不参与签名
			if (null != data.get(k) && data.get(k).trim().length() > 0) {
				sb.append(k).append("=").append(data.get(k).trim()).append("&");
			}
		}

		sb.append("key=").append(key);

		if (WeixinPaySdkConfig.MD5.equals(signType)) {
			return MD5(sb.toString()).toUpperCase();
		} else if (WeixinPaySdkConfig.HMACSHA256.equals(signType)) {
			return HMACSHA256(sb.toString(), key);
		} else {
			throw new Exception(String.format("Invalid sign_type: %s", signType));
		}
	}

	/**
	 * 获取随机字符串 Nonce Str
	 *
	 * @return String 随机字符串
	 */
	public static String generateNonceStr() {
		char[] nonceChars = new char[32];

		for (int index = 0; index < nonceChars.length; ++index) {
			nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
		}

		return new String(nonceChars);
	}

	/**
	 * 生成 MD5
	 *
	 * @param data 待处理数据
	 * @return MD5结果
	 */
	public static String MD5(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] array = md.digest(data.getBytes("UTF-8"));

		StringBuilder sb = new StringBuilder();

		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}

		return sb.toString().toUpperCase();
	}

	/**
	 * 生成 HMACSHA256
	 *
	 * @param data 待处理数据
	 * @param key 密钥
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String HMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

		sha256_HMAC.init(secret_key);

		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

		StringBuilder sb = new StringBuilder();

		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}

		return sb.toString().toUpperCase();
	}

	/**
	 * 日志
	 *
	 * @return
	 */
	/*public static Logger getLogger() {
		Logger logger = LoggerFactory.getLogger("wxpay java sdk");

		return logger;
	}*/

	/**
	 * 获取当前时间戳,单位秒
	 *
	 * @return
	 */
	public static long getCurrentTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前时间戳,单位毫秒
	 *
	 * @return
	 */
	public static long getCurrentTimestampMs() {
		return System.currentTimeMillis();
	}

	/**
	 * 实体转map
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> entityToMap(Object bean) throws Exception {
		Map<String, String> data = BeanUtils.describe(bean);

		String keyName = "class";

		if (data.containsKey(keyName)) {
			data.remove(keyName);
		}

		Map<String, String> map = new HashMap<String, String>();

		// 过滤数据
		for(Map.Entry<String, String> d : data.entrySet()){
			if (d != null) {
				map.put(d.getKey(), d.getValue());
			}
		}
		
		return map;
	}
	
	/**
	 * map转实体
	 * @param c
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static <T> T mapToEntity(Class<T> c, Map<String, String> data) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		// 过滤数据
		for(Map.Entry<String, String> d : data.entrySet()){
			if (d != null) {
				map.put(d.getKey(), d.getValue().replaceAll("<!\\[CDATA\\[", "").replaceAll("]]>", ""));
			}
		}

		T entity = c.newInstance();

		// map转对象
		BeanUtils.populate(entity, map);

		return entity;
	}

	/**
	 * 检查支付方式
	 * @param tradeType
	 * @return
	 */
	public static boolean checkTradeType(String tradeType) {
		return WeixinPaySdkConfig.JSAPI.equals(tradeType) ||
				WeixinPaySdkConfig.NATIVE.equals(tradeType) ||
				WeixinPaySdkConfig.APP.equals(tradeType) ||
				WeixinPaySdkConfig.H5.equals(tradeType);
	}

}