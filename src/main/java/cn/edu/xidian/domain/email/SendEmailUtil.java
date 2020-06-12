package cn.edu.xidian.domain.email;

import cn.edu.xidian.domain.Account;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 发送邮件工具类
 *
 * @Author:
 * @Date：
 */
//@Component 将工具类注入spring容器中
@Component
public class SendEmailUtil {

    /*
     *发送邮件
     * @param toEmail 目的地
     * @param code 唯一激活码
     * @return
     */
    public void sendEmail(String code, String toEmail) throws IOException, AddressException, MessagingException {

        String to = toEmail;
        String subject = "邮箱验证";
        //todo:这里的地址要修改为服务器的地址
        String content = "<html><head></head><body><h1>欢迎您使用XDChannel,激活请复制以下链接到浏览器打开</h1><h3>" +
                "https://localhost:8443/XDChannel/confirmAddAccount?code=" + code
                + "</h3></body></html>";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");

        //发送者的邮箱和授权码
        //todo:上传github之前删除这里的帐号密码
        Authenticator authenticator = new EmailAuthenticator("2904988983@qq.com", "*");
        Session sendMailSession = javax.mail.Session.getDefaultInstance(properties, authenticator);
        MimeMessage mailMessage = new MimeMessage(sendMailSession);
        //邮箱的发送者
        try {
            mailMessage.setFrom(new InternetAddress("2904988983@qq.com"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //邮箱接收
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //发送邮件的标题
        mailMessage.setSubject(subject, "UTF-8");
        //发送邮件的日期
        mailMessage.setSentDate(new Date());

        //MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();

        //创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        //设置邮件的内容的格式和字节码
        html.setContent(content.trim(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        mailMessage.setContent(mainPart);
        Transport.send(mailMessage);
    }
}