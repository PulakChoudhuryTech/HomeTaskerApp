package com.smarthome.hometasker.services.email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.smarthome.hometasker.dao.models.user.UserRegistration;



@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(UserRegistration user, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject("Email Verification");
			mimeMessageHelper.setFrom("noreply@hometasker.com");
			mimeMessageHelper.setTo(user.getEmail());
			mimeMessageHelper.setText("Dear " + user.getEmail() + ",\n\n"
                        + "Please click the following link to verify your email address:\n\n"
                        + "http://localhost:8080/api/user/verify?token="+ token +"");
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
    }
}