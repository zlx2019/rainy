package com.zero.rainy.sample.model;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Zero.
 * <p> Created on 2024/11/6 18:26 </p>
 */
@Data
@Accessors(chain = true)
public class GMailResponseVo {
    private int state;
    private GMailMessage message;

    public static GMailResponseVo ok() {
        GMailResponseVo vo = new GMailResponseVo();
        vo.setState(0);
        String code = RandomUtil.randomNumbers(6);
        GMailMessage message = new GMailMessage()
                .setSubject(code + " is your verification code")
                .setSender("noreply@account.tiktok.com")
                .setContent(code + " is your verification code")
                .setSendTime("1729240886438");
        vo.setMessage(message);
        return vo;
    }

    public static GMailResponseVo waiting(){
        return new GMailResponseVo().setState(1);
    }

    public static GMailResponseVo death(){
        return new GMailResponseVo().setState(2);
    }

    @Data
    @Accessors(chain = true)
    public static class GMailMessage{
        private String subject;
        private String sender;
        private String content;
        private String sendTime;
    }
}
