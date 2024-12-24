package com.zero.rainy.core.utils;

import cn.hutool.core.date.DatePattern;
import com.zero.rainy.core.model.bo.EMailMessage;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.ComparisonTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SentDateTerm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.angus.mail.imap.IMAPFolder;

import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
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
        Properties props = MailUtils.getIMAPConfig(host, port);
        Session session = Session.getInstance(props);
        session.setDebug(true); // 调试模式
        Store store = null;
        Folder inbox = null;
        Folder spamBox = null;
        try {
            store = session.getStore(protocol);
            // 连接服务器
            store.connect(host, username, credential);
            if (store.isConnected()) {
                log.info("Connected to imap server [{}]", host);
            }
            // 获取收件箱，并且以只读模式打开
            // 只读模式不会将邮件标记为已读
            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            log.info("收件总邮件数量: [{}]", inbox.getMessageCount());
            log.info("收件箱有 [{}] 未读邮件!", inbox.getUnreadMessageCount());
            log.info("收件箱有 [{}] 封新的邮件! ", inbox.getNewMessageCount());
            log.info("收件箱有 [{}] 封已删除的邮件! ", inbox.getDeletedMessageCount());

            // 检索条件，三个小时以内的邮件
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_YEAR, -1);
            SentDateTerm term = new SentDateTerm(ComparisonTerm.GE, instance.getTime());

            // 预批量获取优化
            FetchProfile profile = new FetchProfile();
            profile.add(UIDFolder.FetchProfileItem.ENVELOPE); // ENVELOPE 包含了：主题、发件人、收件人、发送时间、消息ID等
            profile.add(FetchProfile.Item.CONTENT_INFO);      // CONTENT_INFO 包含了邮件的内容类型信息，帮助解析正文
            profile.add(FetchProfile.Item.FLAGS);             // FLAGS 可以获取接收时间等信息
            profile.add(IMAPFolder.FetchProfileItem.MESSAGE); // 获取正文内容

            // 优先读取收件箱邮件
            int maxEmails = MAX;
            Message[] inboxMessages = searchMessages(inbox, term, maxEmails);
            if (ArrayUtils.isNotEmpty(inboxMessages)){
                inbox.fetch(inboxMessages, profile);
                log.info("从收件箱读取到 {} 封邮件", inboxMessages.length);
            }
            Message[] allMessages = inboxMessages;
            if (ArrayUtils.isEmpty(inboxMessages) || inboxMessages.length < maxEmails){
                spamBox = getSpamBox(store, host);
                if (Objects.nonNull(spamBox)){
                    // 再从垃圾箱读取
                    spamBox.open(Folder.READ_ONLY);
                    Message[] spamMessages = searchMessages(spamBox, term, maxEmails - inboxMessages.length);
                    if (ArrayUtils.isNotEmpty(spamMessages)){
                        spamBox.fetch(spamMessages, profile);
                        allMessages = ArrayUtils.addAll(inboxMessages, spamMessages);
                        log.info("从垃圾箱读取到 {} 封邮件", spamMessages.length);
                    }
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            for (Message message : allMessages) {
                MimeMessage msg = (MimeMessage) message;
                log.info("=============================================================");
                log.info("邮件主题: {}", msg.getSubject());
                List<String> senders = MailUtils.parserEmailAddress(msg.getFrom());
                log.info("发件人: {}", senders);
                List<String> receive = MailUtils.parserEmailAddress(msg.getRecipients(Message.RecipientType.TO));
                log.info("收件人: {}", receive);
                log.info("发件时间: {}", sdf.format(msg.getSentDate()));
                log.info("收件时间: {}", sdf.format(msg.getReceivedDate()));
                String content = MailUtils.parseEmailContent(message);
                log.info("=============================================================");
            }
        } finally {
            if (inbox != null && inbox.isOpen()) {
                inbox.close(false);
            }
            if (spamBox != null && spamBox.isOpen()) {
                spamBox.close(false);
            }
            if (store != null && store.isConnected()) {
                store.close();
            }
        }
        return List.of();
    }

    public static Folder getSpamInbox(List<Folder> folders) throws MessagingException {
        for (Folder folder : folders) {
            if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0){
                if (StringUtils.containsIgnoreCase(folder.getFullName(), "Spam") ||
                    StringUtils.containsIgnoreCase(folder.getFullName(), "垃圾邮件")||
                    StringUtils.containsIgnoreCase(folder.getFullName(), "Junk")){
                    if (folder.exists()){
                        return folder;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取垃圾收件箱, 不同平台名称可能不同.
     * 微软: Junk
     * 谷歌: [Gmail]/垃圾邮件 | [Gmail]/spam
     */
    public static Folder getSpamBox(Store store, String host) throws MessagingException {
        switch (host.toLowerCase()){
            case "outlook.office365.com", "imap-mail.outlook.com":
                return store.getFolder("Junk");
            case "imap.gmail.com":
                Folder folder = store.getFolder("[Gmail]/spam");
                if (!folder.exists()){
                    folder = store.getFolder("[Gmail]/垃圾邮件");
                    if (folder.exists()){
                        return folder;
                    }
                }
        }
        return null;
    }

    /**
     * 从收件箱或垃圾箱中搜索符合条件的邮件
     *
     * @param folder    收件箱/垃圾箱
     * @param term      搜索条件
     * @param limit     最多获取的邮件数量
     */
    private static Message[] searchMessages(Folder folder, SearchTerm term, int limit) throws MessagingException {
        int total = folder.getMessageCount();
        limit = Math.min(limit, total);
        Message[] messages = folder.getMessages(total - limit + 1, total);
        if (ArrayUtils.isEmpty(messages)){
            return messages;
        }
        messages = folder.search(term, messages);
        return messages;
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
        List<EMailMessage> eMailMessages = fetchEmails("imap", "imap.gmail.com", 993, "zero18674568@gmail.com", "ugdfxonasbwzgvww");
//        List<EMailMessage> eMailMessages = fetchEmails("imap", "outlook.office365.com", 993, "warnochp7192@outlook.com", "EwBYA+l3BAAUcDnR9grBJokeAHaUV8R3+rVHX+IAAWW7evUTtMUhvwKp/lxLHboZkr4D/wSWi++4wXVKXiTk/i1Hn9/G9a8xv1W2EDNu74heZN0Lq3ciqJBOzB65ggTFkh+St0H/WM8mb9vDbKzzs+Yh10rbgRvveHOSXy1D+RKpOrTa80tRfrWweAyhZZMWXwYMMY3VoHIebL6+a7VK6pzLQC6DKz7SjmJilRWunTMiifbipQc02BhxipokB51vh5xHV+JZJsZT/TySvSvUj6GhUSnYzQc7BgymV2B1dGbhqg0/f2BJ0CZMkPq3Kl5PUAZRbEiP81ysxfdrbNbdNxrPffgvA67thpEZv/mYA1Mqyy6paqLvpfZ/hxMC6TAQZgAAEPxhzF+qVxcpdKrcxNxZmTUgAoWQAo738IUzWfsh/x3OjCSJ1MNM7NQ/SwyiA9C/XnPkC73LFhOvt3+yy0dGodzm3lk464mXtebryQduDK66j5m15wt1Cs79xvWtiCNqOhhUJYE91lC+L9Ww3qEHwbovTDZdLPOgDJSb7MLXuaJEzlLAQkVY+HnZMm/nYsPNzWj5z3pi+vPGTaBnxM7vTszzyDOvrHolx9TNmvZdg5FmuLalkNr5a7RG9HonH3pu+ykxLNplDgxvYFYAC+8KfRrUg9kBEm+/skaqhYy4LAZli0GXSzPCs7tDbWNsD3oj5bYSZUCUY+n+QDdWP/Wt5aDDbiiyWA58hNoaLYmKc6esBj4GcpPsnLxpPPL5KR1E7L2cQ9DBp4qkEleu7idV64yf5o8Y5HJ0KgRADGkeI/rfC61JX+9XMyAG0fK+t6DLMRqmkZreYJKI8njyo42KNvBTPUFFTfklteegcdFP7SovPeFJjQ5rSKgpNTB9lx3H+VqxejtXelPcBUJS3XlLggcdW2hYFX2tBrbwsWUk9EqdQkFaXxxc8MKo7nWANcsv0ojrBSjxbv0sVJXAq3vvRZfZYglWUJMhlmstdCpMbcyDosCGenMDBstG0kWb0JoBsSWqpAddIUmROfRvvLLwcIapIrs/HWs/9bKLtaluA534jS9cKMJOEuIL+Z5eb6oV94LhAqpNiiof/W345IzO6nW7NVZ742gPES82QW22Sstf3YFSAg==");
        long end = System.currentTimeMillis();
        System.out.println("用时: " + (end - begin) + "ms");
    }
}
