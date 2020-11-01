package com.fenrir.pay.weixin.sdk;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

/**
 * 微信支付xml工具
 * @author fenrir
 *
 */
public final class WeixinPayXmlUtil {
	
	private static final String DISALLOW_DOCTYPE_DECL = "http://apache.org/xml/features/disallow-doctype-decl";
	
	private static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
	
	private static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
	
	private static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final String FEATURE_SECURE_PROCESSING = "http://javax.xml.XMLConstants/feature/secure-processing";
	
	public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setFeature(DISALLOW_DOCTYPE_DECL, true);
		documentBuilderFactory.setFeature(EXTERNAL_GENERAL_ENTITIES, false);
		documentBuilderFactory.setFeature(EXTERNAL_PARAMETER_ENTITIES, false);
		documentBuilderFactory.setFeature(LOAD_EXTERNAL_DTD, false);
		documentBuilderFactory.setFeature(FEATURE_SECURE_PROCESSING, true);
		documentBuilderFactory.setXIncludeAware(false);
		documentBuilderFactory.setExpandEntityReferences(false);

		return documentBuilderFactory.newDocumentBuilder();
	}

	public static Document newDocument() throws ParserConfigurationException {
		return newDocumentBuilder().newDocument();
	}
}