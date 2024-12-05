package com.zero.rainy;

import com.zero.rainy.sample.model.MailMessage;
import com.zero.rainy.sample.model.MailStorage;
import com.zero.rainy.sample.service.MailTemporaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * @author Zero.
 * <p> Created on 2024/12/5 10:32 </p>
 */
@SpringBootTest
public class RedisHashTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MailTemporaryRepository mailTemporaryRepository;

    @Test
    public void test() {
        MailStorage storage = new MailStorage("zero18674568@gmail.com");
        MailMessage message1 = new MailMessage();
        message1.setSender("github@nphudaw.com");
        message1.setContent("""
                <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional //EN"><html><head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"><title>Facebook</title><style nonce="rTpwosrJ">@media all and (max-width: 480px){*[class].ib_t{min-width:100% !important}*[class].ib_row{display:block !important}*[class].ib_ext{display:block !important;padding:10px 0 5px 0;vertical-align:top !important;width:100% !important}*[class].ib_img,*[class].ib_mid{vertical-align:top !important}*[class].mb_blk{display:block !important;padding-bottom:10px;width:100% !important}*[class].mb_hide{display:none !important}*[class].mb_inl{display:inline !important}*[class].d_mb_flex{display:block !important}}.d_mb_show{display:none}.d_mb_flex{display:flex}@media only screen and (max-device-width: 480px){.d_mb_hide{display:none !important}.d_mb_show{display:block !important}.d_mb_flex{display:block !important}}.mb_text h1,.mb_text h2,.mb_text h3,.mb_text h4,.mb_text h5,.mb_text h6{line-height:normal}.mb_work_text h1{font-size:19px;line-height:normal;margin-top:4px}.mb_work_text h2,.mb_work_text h3{font-size:17px;line-height:normal;margin-top:4px}.mb_work_text h4,.mb_work_text h5,.mb_work_text h6{font-size:15px;line-height:normal}.mb_work_text a{color:#1270e9}.mb_work_text p{margin-top:4px}</style></head><body style="margin:0;padding:0;" dir="ltr" bgcolor="#ffffff"><table border="0" cellspacing="0" cellpadding="0" align="center" id="email_table" style="border-collapse:collapse;"><tr><td id="email_content" style="font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;background:#ffffff;"><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr style=""><td height="20" style="line-height:20px;" colspan="3">&nbsp;</td></tr><tr><td height="1" colspan="3" style="line-height:1px;"></td></tr><tr><td style=""><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;text-align:center;html_width:100%;width:100%;"><tr><td width="15px" style="width:15px;"></td><td style="line-height:0px;max-width:600px;padding:0 0 15px 0;"><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr><td style="width:100%;text-align:left;height:33px;"><img height="33" src="https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/Otjcwa2eCOF.png" style="border:0;"></td></tr></table></td><td width="15px" style="width:15px;"></td></tr></table></td></tr><tr><td style=""><table border="0" width="430" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:0 auto 0 auto;"><tr><td style=""><table border="0" width="430px" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:0 auto 0 auto;width:430px;"><td width="15" style="display:block;width:15px;">&nbsp;&nbsp;&nbsp;</td><tr><td width="12" style="display:block;width:12px;">&nbsp;&nbsp;&nbsp;</td><td style=""><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr><td><td style="margin:10px 0 10px 0;color:#565a5c;font-size:18px;"><p style="margin:10px 0 10px 0;color:#565a5c;font-size:18px;">你好，</p> <p style="margin:10px 0 10px 0;color:#565a5c;font-size:18px;">有人尝试用 z44ql9i7u5@outlook.com 注册 Instagram 帐户，如果是你本人，请在应用中输入验证码：</p></td></td></tr><tr><td><td style="padding:10px;color:#565a5c;font-size:32px;font-weight:500;text-align:center;padding-bottom:25px;">153894</td></td></tr></table></td></tr></table></td></tr></table></td></tr><tr><td style=""><table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:0 auto 0 auto;width:100%;max-width:600px;"><tr style=""><td height="4" style="line-height:4px;" colspan="3">&nbsp;</td></tr><tr><td width="15px" style="width:15px;"></td><td width="20" style="display:block;width:20px;">&nbsp;&nbsp;&nbsp;</td><td style="text-align:center;"><div style="padding-top:10px;display:flex;"><div style="margin:auto;"><img class="img" src="https://static.xx.fbcdn.net/rsrc.php/v3/y3/r/Bqo9-L659wB.png" height="26" width="52" alt=""></div><br></div><div style="height:10px;"></div><div style="color:#abadae;font-size:11px;margin:0 auto 5px auto;">© Instagram. Meta Platforms, Inc., 1601 Willow Road, Menlo Park, CA 94025<br></div><div style="color:#abadae;font-size:11px;margin:0 auto 5px auto;">这封邮件已发给 <a style="color:#abadae;text-decoration:underline;">z44ql9i7u5@outlook.com</a> 。<br></div></td><td width="20" style="display:block;width:20px;">&nbsp;&nbsp;&nbsp;</td><td width="15px" style="width:15px;"></td></tr><tr style=""><td height="32" style="line-height:32px;" colspan="3">&nbsp;</td></tr></table></td></tr><tr style=""><td height="20" style="line-height:20px;" colspan="3">&nbsp;</td></tr></table><span style=""><img src="https://www.facebook.com/email_open_log_pic.php?mid=62753b7c01f76G24bc2cdafa4000G6275401562248G37f" style="border:0;width:1px;height:1px;"></span></td></tr></table></body></html>
                """);
        message1.setSubject("github code");
        message1.setRecvTimeStamp(System.currentTimeMillis());
        message1.setSendTimeStamp(System.currentTimeMillis());

        MailMessage message2 = new MailMessage();
        message2.setSender("Twitter@nphudaw.com");
        message2.setContent("""
                <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional //EN"><html><head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"><title>Facebook</title><style nonce="rTpwosrJ">@media all and (max-width: 480px){*[class].ib_t{min-width:100% !important}*[class].ib_row{display:block !important}*[class].ib_ext{display:block !important;padding:10px 0 5px 0;vertical-align:top !important;width:100% !important}*[class].ib_img,*[class].ib_mid{vertical-align:top !important}*[class].mb_blk{display:block !important;padding-bottom:10px;width:100% !important}*[class].mb_hide{display:none !important}*[class].mb_inl{display:inline !important}*[class].d_mb_flex{display:block !important}}.d_mb_show{display:none}.d_mb_flex{display:flex}@media only screen and (max-device-width: 480px){.d_mb_hide{display:none !important}.d_mb_show{display:block !important}.d_mb_flex{display:block !important}}.mb_text h1,.mb_text h2,.mb_text h3,.mb_text h4,.mb_text h5,.mb_text h6{line-height:normal}.mb_work_text h1{font-size:19px;line-height:normal;margin-top:4px}.mb_work_text h2,.mb_work_text h3{font-size:17px;line-height:normal;margin-top:4px}.mb_work_text h4,.mb_work_text h5,.mb_work_text h6{font-size:15px;line-height:normal}.mb_work_text a{color:#1270e9}.mb_work_text p{margin-top:4px}</style></head><body style="margin:0;padding:0;" dir="ltr" bgcolor="#ffffff"><table border="0" cellspacing="0" cellpadding="0" align="center" id="email_table" style="border-collapse:collapse;"><tr><td id="email_content" style="font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;background:#ffffff;"><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr style=""><td height="20" style="line-height:20px;" colspan="3">&nbsp;</td></tr><tr><td height="1" colspan="3" style="line-height:1px;"></td></tr><tr><td style=""><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;text-align:center;html_width:100%;width:100%;"><tr><td width="15px" style="width:15px;"></td><td style="line-height:0px;max-width:600px;padding:0 0 15px 0;"><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr><td style="width:100%;text-align:left;height:33px;"><img height="33" src="https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/Otjcwa2eCOF.png" style="border:0;"></td></tr></table></td><td width="15px" style="width:15px;"></td></tr></table></td></tr><tr><td style=""><table border="0" width="430" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:0 auto 0 auto;"><tr><td style=""><table border="0" width="430px" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:0 auto 0 auto;width:430px;"><td width="15" style="display:block;width:15px;">&nbsp;&nbsp;&nbsp;</td><tr><td width="12" style="display:block;width:12px;">&nbsp;&nbsp;&nbsp;</td><td style=""><table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr><td><td style="margin:10px 0 10px 0;color:#565a5c;font-size:18px;"><p style="margin:10px 0 10px 0;color:#565a5c;font-size:18px;">你好，</p> <p style="margin:10px 0 10px 0;color:#565a5c;font-size:18px;">有人尝试用 z44ql9i7u5@outlook.com 注册 Instagram 帐户，如果是你本人，请在应用中输入验证码：</p></td></td></tr><tr><td><td style="padding:10px;color:#565a5c;font-size:32px;font-weight:500;text-align:center;padding-bottom:25px;">153894</td></td></tr></table></td></tr></table></td></tr></table></td></tr><tr><td style=""><table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:0 auto 0 auto;width:100%;max-width:600px;"><tr style=""><td height="4" style="line-height:4px;" colspan="3">&nbsp;</td></tr><tr><td width="15px" style="width:15px;"></td><td width="20" style="display:block;width:20px;">&nbsp;&nbsp;&nbsp;</td><td style="text-align:center;"><div style="padding-top:10px;display:flex;"><div style="margin:auto;"><img class="img" src="https://static.xx.fbcdn.net/rsrc.php/v3/y3/r/Bqo9-L659wB.png" height="26" width="52" alt=""></div><br></div><div style="height:10px;"></div><div style="color:#abadae;font-size:11px;margin:0 auto 5px auto;">© Instagram. Meta Platforms, Inc., 1601 Willow Road, Menlo Park, CA 94025<br></div><div style="color:#abadae;font-size:11px;margin:0 auto 5px auto;">这封邮件已发给 <a style="color:#abadae;text-decoration:underline;">z44ql9i7u5@outlook.com</a> 。<br></div></td><td width="20" style="display:block;width:20px;">&nbsp;&nbsp;&nbsp;</td><td width="15px" style="width:15px;"></td></tr><tr style=""><td height="32" style="line-height:32px;" colspan="3">&nbsp;</td></tr></table></td></tr><tr style=""><td height="20" style="line-height:20px;" colspan="3">&nbsp;</td></tr></table><span style=""><img src="https://www.facebook.com/email_open_log_pic.php?mid=62753b7c01f76G24bc2cdafa4000G6275401562248G37f" style="border:0;width:1px;height:1px;"></span></td></tr></table></body></html>
                """);
        message2.setSubject("Twitter code");
        message2.setRecvTimeStamp(System.currentTimeMillis());
        message2.setSendTimeStamp(System.currentTimeMillis());
        storage.put("github@nopoy1.com", message1);
        storage.put("twitter@nopoy1.com", message2);
        mailTemporaryRepository.putAll(storage);

        Map<String, MailMessage> messageMap = mailTemporaryRepository.getAll(storage.getKey());
        System.out.println(messageMap);

        System.out.println(mailTemporaryRepository.hasExists(storage.getKey(), "twitter@nopoy1.com"));
        System.out.println(mailTemporaryRepository.hasExists(storage.getKey(), "xzczs"));

        System.out.println(mailTemporaryRepository.get(storage.getKey(), "twitter@nopoy1.com"));
        System.out.println(mailTemporaryRepository.get(storage.getKey(), "dawdw"));

        System.out.println(11);
        mailTemporaryRepository.delete(storage.getKey());
//        MailStorage store = mailTemporaryRepository.getStore(storage.getKey());
//        System.out.println(store);
    }
}
