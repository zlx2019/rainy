package com.zero.rainy.core.model.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 电子邮件信息实体
 *
 * @author Zero.
 * <p> Created on 2024/12/10 15:17 </p>
 */
public class EMailMessage {
    private String from;        // 发件人
    private List<String> to;    // 收件人列表

    private String subject;     // 邮件主题
    private String content;     // 邮件正文
    private Date sentDate;      // 邮件发送时间
    private Date receivedDate;  // 接收邮件时间

    // ext
    private List<String> flags; // 邮件状态标记
    private List<String> cc;    // 抄送邮箱列表
    private List<String> bcc;   // 密送邮箱列表

    @Data
    public static class Body{
        private StringBuilder textContent;
        private StringBuilder htmlContent;
        public Body(){
            this.htmlContent = new StringBuilder();
            this.textContent = new StringBuilder();
        }
    }
}
