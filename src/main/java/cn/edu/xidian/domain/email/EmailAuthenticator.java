package cn.edu.xidian.domain.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by 胡广鹏 on 2020/6/10 16:55
 */
public class EmailAuthenticator extends Authenticator {
    private String userName = null;
    private String passWord = null;
    public EmailAuthenticator(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName,passWord);
    }
}
