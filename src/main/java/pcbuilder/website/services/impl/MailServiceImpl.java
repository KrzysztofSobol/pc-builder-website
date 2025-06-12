package pcbuilder.website.services.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pcbuilder.website.services.MailService;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class MailServiceImpl implements MailService {
    private final static Logger log =
            Logger.getLogger(MailServiceImpl.class.getName());
    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        try {
            log.finer("Initializing Mail Service...");
            this.mailSender = mailSender;
        } catch (Exception e) {
            log.severe("Failed to initialize Mail Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("build@gmail.com"); // Adres nadawcy

        try {
            log.fine("Sending email...");
            mailSender.send(message);
        } catch (MailException e) {
            log.severe("Failed to send email error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(null);
    }
}
