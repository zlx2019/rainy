package com.zero.rainy.sample.pay;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * @author Zero.
 * <p> Created on 2024/9/22 19:46 </p>
 */
@Data
// 字段名按照 ASCII 码顺序序列化
@JsonPropertyOrder(alphabetic = true)
public class PayRequest {
    @JsonProperty("order_id")
    private String orderId;

    private String amount;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("redirect_url")
    private String redirectUrl;

    public String getHash(String key) {
        String format = StrUtil.format("amount={}&notify_url={}&order_id={}&redirect_url={}",
                amount, notifyUrl, orderId, redirectUrl);
        return format + key;
    }
}
