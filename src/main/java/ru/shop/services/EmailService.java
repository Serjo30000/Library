package ru.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;
@Service
@AllArgsConstructor
public class EmailService {
    public void sendBuyBookEmail(String to, double price)throws AddressException, MessagingException, IOException {
        String from = ""; // your email
        String pass = ""; // your pass
        //Пароль приложения: your pass
        String host = "smtp.yandex.ru";
        Properties props = new Properties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session sessionPost = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,pass);
            }
        });

        try {
            Message msg = new MimeMessage(sessionPost);
            String subject = "Заказ от 'Library'";
            String body = "Заказ от 'Library':\n";
            body+="Общая цена: "+price+"₽\n";
            msg.setSubject(subject);
            msg.setText(body);
            msg.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        }
        catch (AddressException ae) {
            System.out.println("1");
        }
        catch (MessagingException me) {
            System.out.println("2");
        }
    }

    public void sendDeleteBookEmail(String to, double price)throws AddressException, MessagingException, IOException {
        String from = ""; // your email
        String pass = ""; // your pass
        //Пароль приложения: your pass
        String host = "smtp.yandex.ru";
        Properties props = new Properties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session sessionPost = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,pass);
            }
        });

        try {
            Message msg = new MimeMessage(sessionPost);
            String subject = "Отмена заказа от 'Library'";
            String body = "Отмена заказа от 'Library':\n";
            body+="Возврат средств: "+price+"₽\n";
            msg.setSubject(subject);
            msg.setText(body);
            msg.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        }
        catch (AddressException ae) {
            System.out.println("1");
        }
        catch (MessagingException me) {
            System.out.println("2");
        }
    }
}
