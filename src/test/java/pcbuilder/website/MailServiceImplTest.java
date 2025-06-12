package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pcbuilder.website.services.impl.MailServiceImpl;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> mailCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendEmailSuccessfully() {
        // given
        String to = "user@example.com";
        String subject = "Test Subject";
        String text = "Test message body";

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        CompletableFuture<Void> result = mailService.sendEmail(to, subject, text);

        // then
        assertNotNull(result);
        assertTrue(result.isDone());

        // verify message
        verify(mailSender).send(mailCaptor.capture());
        SimpleMailMessage message = mailCaptor.getValue();
        assertArrayEquals(new String[] { to }, message.getTo());
        assertEquals(subject, message.getSubject());
        assertEquals(text, message.getText());
        assertEquals("build@gmail.com", message.getFrom());
    }
}