package Services.Impl;

import Constants.MailGunConstant;
import Models.Email;
import Services.EmailService;
import jakarta.enterprise.inject.Default;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;

/**
 *
 * @author nguyenson
 */
@Default
public class MailgunEmailServiceImpl implements EmailService {

    @Override
    public Email sendMail(Email email) {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(MailGunConstant.BASE_URL, MailGunConstant.API_KEY)
                .createApi(MailgunMessagesApi.class);

        Message message = Message.builder()
                .from(email.getFrom())
                .to(email.getTo())
                .subject(email.getSubject())
                .html(email.getHtml())
                .build();

        MessageResponse response = mailgunMessagesApi.sendMessage(MailGunConstant.DOMAIN_NAME, message);

        return null;
    }
    
    public static void main(String[] args) {
        MailgunEmailServiceImpl m = new MailgunEmailServiceImpl();
        String content = "If the reason why your messages are being delivered to a recipient's spam folder is due to the server's settings, the quickest way to bypass this would be to invite your recipients to safelist your sending domain (found in your Mailgun control panel - make sure it's the exact one!). You could also have the recipient safelist the sending IP, however, this is really only advisable with a dedicated IP, since shared IP assignments are essentially dynamic; they could be changed automatically by our system without notification. Safelisting the sending IP should only ever be a secondary option to safelisting the sending domain!";
        Email email = Email.builder()
                .from("account@oi.io.vn <account@oi.io.vn>")
                .to("sonqt123456@gmail.com")
                .subject("Password reset")
                .html("You are going to reset password for account with username son. Click this <a href=\"oi.io.vn\">link</a> to reset your password! Thank you!")
                .build();
        m.sendMail(email);
        
    }

}
