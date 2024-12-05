package com.zero.rainy.sample.model;

import lombok.Data;

/**
 * 单封邮件信息
 *
 * @author Zero.
 * <p> Created on 2024/12/5 10:43 </p>
 */
@Data
public class MailMessage {
    private String subject;
    private String content;
    private String sender;
    private long sendTimeStamp;
    private long recvTimeStamp;
}
