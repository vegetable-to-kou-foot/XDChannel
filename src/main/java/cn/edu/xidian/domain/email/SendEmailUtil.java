package cn.edu.xidian.domain.email;

import cn.edu.xidian.domain.Account;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

    public static void sendEmail(String code, String to) {
        String content = "<html><head></head><body><h1>欢迎您使用XDChannel,激活请复制以下链接到浏览器打开</h1><h3>" +
                "https://39.99.203.158:8443/XDChannel/confirmAddAccount?code=" + code
                + "</h3></body></html>";
        Properties props = new Properties();
        props.put("username", "2904988983@qq.com");
        props.put("password", "*");

        //网易的smtp服务器地址
        props.put("mail.smtp.host", "smtp.qq.com");
        //SSLSocketFactory类的端口
        props.put("mail.smtp.socketFactory.port", "465");
        //SSLSocketFactory类
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        //网易提供的ssl加密端口,QQ邮箱也是该端口
        props.put("mail.smtp.port", "465");

        Session defaultInstance = Session.getDefaultInstance(props);
        try {
            Session session = Session.getInstance(props, null);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("username")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("XDChannel 注册验证");
            // 正文和响应头
            message.setContent(content, "text/html;charset=UTF-8");
            message.saveChanges();
            Transport transport = defaultInstance.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("username"), props.getProperty("password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}