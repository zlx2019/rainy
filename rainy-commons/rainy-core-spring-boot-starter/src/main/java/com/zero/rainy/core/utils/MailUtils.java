package com.zero.rainy.core.utils;

import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author Zero.
 * <p> Created on 2024/12/10 16:56 </p>
 */
public class MailUtils {


    public static List<String> parserEmailAddress(Address[] addresses) {
        if (addresses == null || addresses.length == 0) {
            return List.of();
        }
        return Arrays.stream(addresses)
                .map(address -> ((InternetAddress) address).getAddress())
                .collect(Collectors.toList());
    }

    /**
     * 将 Address[] 转换为带名称的邮箱地址列表
     * 格式: "John Doe <john@example.com>"
     */
    public static List<String> parseEmailAddressWithNames(Address[] addresses) {
        if (addresses == null || addresses.length == 0) {
            return List.of();
        }
        return Arrays.stream(addresses)
                .map(address -> {
                    InternetAddress internetAddress = (InternetAddress) address;
                    String personal = internetAddress.getPersonal();
                    String email = internetAddress.getAddress();
                    if (personal != null && !personal.isEmpty()) {
                        return String.format("%s <%s>", personal, email);
                    }
                    return email;
                })
                .collect(Collectors.toList());
    }

    /**
     * 解析邮件正文为 String
     *
     * @param message 要解析的邮件对象
     * @return 邮件正文内容（HTML 或纯文本）
     * @throws Exception 当解析失败时抛出异常
     */
    public static String parseEmailContent(Message message) throws Exception {
        Object content = message.getContent();
        System.out.println(message.getContentType());
        if (content instanceof String) {
            // 如果是简单的文本内容，直接返回
            return (String) content;
        } else if (content instanceof MimeMultipart) {
            // 如果是多部分内容，解析 multipart
            return getTextFromMimeMultipart((MimeMultipart) content);
        }
        return null;
    }

    /**
     * 解析 MimeMultipart 内容，提取 HTML 或纯文本内容
     *
     * @param mimeMultipart 多部分邮件内容
     * @return 邮件正文内容（HTML 或纯文本）
     * @throws Exception 当解析失败时抛出异常
     */
    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                // 如果是纯文本，获取内容
                result.append((String) bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                // 如果是 HTML，优先返回 HTML 格式的内容
                String html = (String) bodyPart.getContent();
                result.append(html);
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                // 如果是嵌套的 multipart，递归解析
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

    public static Properties getIMAPConfig(String host, int port){
        return getConfigs("imap", host, port, false);
    }

    /**
     * 构建 邮件协议 Properties 配置
     * @param protocol  协议
     * @param host      主机
     * @param port      端口
     */
    private static Properties getConfigs(String protocol, String host, int port, boolean sasl) {
        Properties props = new Properties(12);
        // 目标服务器信息
        props.put("mail." + protocol + ".host", host);
        props.put("mail." + protocol + ".port", port);
        // 连接服务器超时时间
        props.put("mail." + protocol + ".connectiontimeout", 15_000);
        // 读取邮件超时时间
        props.put("mail." + protocol + ".timeout", 15_000);

        // 开启认证 & ssl/tls
        props.put("mail." + protocol + ".auth", true);
        props.put("mail." + protocol + ".ssl.enable", true);
        if (sasl){
            // XOAUTH2 认证
            props.put("mail." + protocol + ".auth.enable", true);
            props.put("mail." + protocol + ".auth.mechanisms", "XOAUTH2");
        }
        return props;
    }
}
