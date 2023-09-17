package com.example.hicardipresscenter.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;

    /*
    * 하이카디 소식 새 글 알림
    * 링크로 연결
     */
    private MimeMessage createMessage(String to, MessageType type, String id) throws Exception {
        log.info("보내는 대상 : " + to);

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);

        message.setSubject(type.getSubject(), "utf-8");

        String msgg = "<div style='max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif;'>";
        msgg += "<h1 style='color: #00428B; text-align: center;'>" + type.getSubject() + "</h1>";
        msgg += "<p style='text-align: center;'>" + type.getContent() + "</p>";
        msgg += "<p style='text-align: center;'>아래 링크를 클릭해주세요</p>";
        msgg += "<div style='background-color: #f0f0f0; border: 1px solid #ccc; padding: 15px; text-align: center;'>";
        msgg += "<a href=" + "'" + type.getLink() + id + "'" + " style='font-size: 20px; font-weight: bold;'>페이지 바로가기</a>";
        msgg += "</div>";
        msgg += "<p style='text-align: center; font-size: 16px;'>감사합니다.</p>";
        msgg += "</div>";

        message.setText(msgg, "utf-8", "html");
        message.setFrom("HICARDI");

        return message;
    }

    public void sendSimpleMessage(String to, MessageType messageType, String id) throws Exception {

        MimeMessage message = createMessage(to, messageType, id);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
