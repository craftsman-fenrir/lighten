package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关闭订单请求实体
 * @author fenrir
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CloseOrderRequestEntity {

    /**
     * appid（必填）
     */
    private String appid;

    /**
     * 商户号（必填）
     */
    private String mch_id;

    /**
     * 商户订单号（必填）
     */
    private String out_trade_no;

    /**
     * 随机字符串（必填）
     */
    private String nonce_str;

    /**
     * 签名（必填）
     */
    private String sign;

    /**
     * 签名类型（可选）
     */
    private String sign_type;

}