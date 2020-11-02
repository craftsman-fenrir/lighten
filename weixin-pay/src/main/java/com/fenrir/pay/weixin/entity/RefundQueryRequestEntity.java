package com.fenrir.pay.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求实体-查询退款
 * @author fenrir
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundQueryRequestEntity {

    /**
     * 微信订单号（四选一）
     */
    private String transaction_id;

    /**
     * 商户订单号（四选一）
     */
    private String out_trade_no;

    /**
     * 商户退款单号（四选一）
     */
    private String out_refund_no;

    /**
     * 微信退款单号（四选一）
     */
    private String refund_id;

    /**
     * 偏移量（可选） 偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录
     */
    private Long offset;

}