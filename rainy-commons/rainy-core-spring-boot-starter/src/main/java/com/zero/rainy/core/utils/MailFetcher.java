package com.zero.rainy.core.utils;

import cn.hutool.core.date.DatePattern;
import com.zero.rainy.core.model.bo.EMailMessage;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.ComparisonTerm;
import jakarta.mail.search.SentDateTerm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * 邮件提取工具，支持 IMAP/POP3 协议
 *
 * @author Zero.
 * <p> Created on 2024/12/10 14:44 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailFetcher {
    private static final Stream<String> PROTOCOLS = Stream.of("imap", "pop3");
    private static final int MAX = 10;

    /**
     * 连接邮箱服务器读取邮件列表
     *
     * @return {@link List<EMailMessage>}
     */
    public static List<EMailMessage> fetchEmails(String protocol, String host, int port, String username, String credential) throws Exception {
        if (!checkProtocol(protocol)) {
            throw new ProtocolException("protocol is not supported: " + protocol);
        }
        Properties props = PropertiesUtils.getIMAPConfig(protocol, host, port);
        Session session = Session.getInstance(props);
        session.setDebug(false); // 调试模式
        Store store = null;
        Folder inbox = null;
        Folder trash = null;
        try {
            store = session.getStore(protocol);
            // 连接服务器
            store.connect(host, username, credential);
            if (store.isConnected()) {
                log.info("Connected to imap server [{}]", host);
            }
            // 获取收件箱，并且以只读模式打开
            // 只读模式不会将邮件标记为已读
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            log.info("收件总邮件数量: [{}]", inbox.getMessageCount());
            log.info("收件箱有 [{}] 未读邮件!", inbox.getUnreadMessageCount());
            log.info("收件箱有 [{}] 封新的邮件! ", inbox.getNewMessageCount());
            log.info("收件箱有 [{}] 封已删除的邮件! ", inbox.getDeletedMessageCount());

            // 限制读最新的N封邮件
            int messageCount = inbox.getMessageCount();
            int maxReads = Math.min(MAX, messageCount);
            Message[] messages = inbox.getMessages(messageCount - maxReads + 1, messageCount);
            System.out.println(messages.length);
            // 只需要1天前，至现在的邮件
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_YEAR, -1);
            SentDateTerm term = new SentDateTerm(ComparisonTerm.GE, instance.getTime());
            messages = inbox.search(term, messages);
            System.out.println(messages.length);

            // 批量读取所需邮件内容
            FetchProfile profile = new FetchProfile();
            profile.add(UIDFolder.FetchProfileItem.ENVELOPE); // ENVELOPE 包含了：主题、发件人、收件人、发送时间、消息ID等
            profile.add(FetchProfile.Item.CONTENT_INFO);      // CONTENT_INFO 包含了邮件的内容类型信息，帮助解析正文
            profile.add(FetchProfile.Item.FLAGS);             // FLAGS 可以获取接收时间等信息
            inbox.fetch(messages, profile);
            SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            for (Message message : messages) {
                MimeMessage msg = (MimeMessage) message;
                log.info("=============================================================");
                log.info("邮件主题: {}", msg.getSubject());
                List<String> senders = EMailParseUtils.parserEmailAddress(msg.getFrom());
                log.info("发件人: {}", senders);
                List<String> receive = EMailParseUtils.parserEmailAddress(msg.getRecipients(Message.RecipientType.TO));
                log.info("收件人: {}", receive);
                log.info("发件时间: {}", sdf.format(msg.getSentDate()));
                log.info("收件时间: {}", sdf.format(msg.getReceivedDate()));
                String content = EMailParseUtils.parseEmailContent(message);
                log.info("邮件正文: {}", content);
                log.info("=============================================================");
            }
        } finally {
            if (inbox != null && inbox.isOpen()) {
                inbox.close(false);
            }
            if (trash != null && trash.isOpen()) {
                trash.close(false);
            }
            if (store != null && store.isConnected()) {
                store.close();
            }
        }
        return List.of();
    }

    /**
     * check protocol
     */
    private static boolean checkProtocol(String protocol) {
        if (StringUtils.isBlank(protocol)) {
            return false;
        }
        return PROTOCOLS.anyMatch(protocol::equalsIgnoreCase);
    }

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();
//        List<EMailMessage> eMailMessages = fetchEmails("imap", "imap.gmail.com", 993, "zero18674568@gmail.com", "ugdfxonasbwzgvww");
        List<EMailMessage> eMailMessages = fetchEmails("imap", "outlook.office365.com", 993, "warnochp7192@outlook.com", "EwBYA+l3BAAUcDnR9grBJokeAHaUV8R3+rVHX+IAAaHNNMEFkYjmd4hC1St5+vVKCqctAGFE/IgW06a1iIxCWLXaV0mY9UgBwEuES6tErfDXkdmU1hYseGMadCR3bamzVuxEycYUZhSvBYWoeE2a44CMrWREN540s4hBNRgmLmrpaf63v1+1OOsr36HizSGP6dvZk2BT/pvPJqu3GMpKXxtFIAsN20V4ov+ypuIftPu4zfiDTUCGNBS0WAHTMa0aFlbflQnSevyOkc3O1iyO8o+lI59cpJcv1HpKfeO76v8h+p2bgwwZ6qiR0ezfS72hXUxOybYKEHuCg+tcZ1NTRWyO5BICrBT8dxheunr7OgK27qLHevnY3eTouxXwCrgQZgAAEIIFxoRaPKhagoyYTZpM9WQgAogf/sNSL1i5MZl60aumaBXvtC9tcfPBDliLQ7+TfBAEW6XgCZQ8cZqa6wQhFWVaf5DeH3rGvMDFm1N311XkfZZeN+PRl++ZKDPSL4UPh8J/NbUbpZTZTEcM5sn3eBCPodgH5cVY+vhZuAOenTnE0JMyuMW3f6fBSAa3hRj/tIGy0QyPfX6S9JRoKlgnACY2sbXdRXyEDTJyZXVVzAtv642XXi9An9Tc8NbxC0nNBvrTfzmzyaVfgjHaRRz8/llGzYc5oq9kn2jqFVNUt8gGYvgWQh1C02smmXJ3AYXDjsy8bY/1JAiOdgXhN0U0uRkzvhzW4llLo/XG6zBu/jqb+xNRABZ5OsFvZp/GmIJu2cFlbBojz0kiAetlC5kqFhAj6xonSFthBYcEEOPm/6mF9iT3HALXZe+sh8QKcWZ7pgZPsVOOVekXUQ+rQ+MEvv8keQVrwYt0+bgY3xK2lYeZq8l0Y5J4ZCLo58sX28jBQcztmgky2kaoysAqqlh6sXjlPqEtRqPjmt2chBPotP0OSUndzsrzbjfs9Hypu0M0JJ50sg80tJcVba8NH02vamOLu7b0Lxb7Uw96GkWPLdC5K60ax0S2fBBDXX20wH5P5JWpvUi2S1cqbEgnhE6GRty2VjcO7iG9TYlYkhQDwzo8Z5GB/+4u8r8S8ViH9DY/GOfMDbElDDC7er90wK5GXIZq/JRe6rGVf5SBlgSE57a3jU5SAg==");
        long end = System.currentTimeMillis();
        System.out.println("用时: " + (end - begin) + "ms");
    }
}
