package com.zero.rainy.sample.pay;

import cn.hutool.crypto.digest.DigestUtil;

import java.math.BigDecimal;

/**
 * @author Zero.
 * <p> Created on 2024/9/22 19:53 </p>
 */
public class Demo {
    public static void main(String[] args) {
        // 请求参数
        PayRequest request = new PayRequest();
        request.setOrderId("2024092221312321321");
        request.setAmount(BigDecimal.valueOf(1));
        request.setNotifyUrl("https://www.ez-captcha.com/pay/notify");
        request.setRedirectUrl("https://www.ez-captcha.com/");

        // 获取请求签名
        String format = request.getHash("af36f887-19e8-5bce-e8a5-199e7c075b68");
        System.out.println(format);
        String sign = DigestUtil.md5Hex(format);
        System.out.println(sign);
//        String json = JsonUtils.toJson(request);
//        System.out.println(json);

        // 签名
//        String hash = DigestUtil.md5Hex(json);
//        String sign = hash.toLowerCase();
//        System.out.println(hash);
//        System.out.println(sign);
    }

}
