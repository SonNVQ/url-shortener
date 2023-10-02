package com.urlshortener.Services.Impl;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import com.urlshortener.Models.Email;
import com.urlshortener.Services.EmailService;
import com.urlshortener.Utils.DotEnv;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailGunEmailServiceImpl implements EmailService {

    private static MailGunEmailServiceImpl mailGunEmailServiceInstance;

    private static MailgunMessagesApi mailgunMessagesApi;

    private static String DOMAIN;

    private MailGunEmailServiceImpl() {
        mailgunMessagesApi = MailgunClient.config(DotEnv.get("MAILGUN_BASE_URL"), DotEnv.get("MAILGUN_API_KEY"))
                .createApi(MailgunMessagesApi.class);
        DOMAIN = DotEnv.get("MAILGUN_DOMAIN");
    }

    public static MailGunEmailServiceImpl load() {
        if (mailGunEmailServiceInstance == null) {
            mailGunEmailServiceInstance = new MailGunEmailServiceImpl();
        }
        return mailGunEmailServiceInstance;
    }

    @Override
    public Email sendMail(Email email) {
        Message message = Message.builder()
                .from(email.getFrom())
                .to(email.getTo())
                .subject(email.getSubject())
                .html(email.getHtml())
                .build();
        try {
            MessageResponse response = mailgunMessagesApi.sendMessage(DOMAIN, message);
            return email;
        } catch (Exception e) {
            Logger.getLogger(MailGunEmailServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static void main(String[] args) {
        MailGunEmailServiceImpl m = new MailGunEmailServiceImpl();
        Email email = Email.builder()
                .from("iLearn <account@ilearn.io.vn>")
                .to("sonnvqhe176602@fpt.edu.vn")
                .subject("Test")
                .html("You are going to reset password for account with username ducduy. Click this <a href=\"\">link</a> to reset your password! Thank you!")
                .build();
        m.sendMail(email);
    }

}
