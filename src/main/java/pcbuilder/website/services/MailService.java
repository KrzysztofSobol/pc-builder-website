package pcbuilder.website.services;

import java.util.concurrent.CompletableFuture;

public interface MailService {
    CompletableFuture<Void> sendEmail(String to, String subject, String text);
}
