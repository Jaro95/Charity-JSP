package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationEmail(String to, String token) {
        String subject = "Complete Registration";
        String confirmationUrl = "http://localhost:8080/charity/verification?token=" + token;
        String message = "To confirm your account, please click here : " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        emailSender.send(email);
    }
}
