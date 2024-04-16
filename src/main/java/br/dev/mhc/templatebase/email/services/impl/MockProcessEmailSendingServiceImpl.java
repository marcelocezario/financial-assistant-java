package br.dev.mhc.templatebase.email.services.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.messaging.client.interfaces.IMessagingClientService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class MockProcessEmailSendingServiceImpl extends AbstractProcessEmailSendingServiceImpl {

    private static final LogHelper LOG = new LogHelper(MockProcessEmailSendingServiceImpl.class);

    public MockProcessEmailSendingServiceImpl(TemplateEngine templateEngine, MailSender mailSender, JavaMailSender javaMailSender, IMessagingClientService messagingClientService) {
        super(templateEngine, mailSender, javaMailSender);
        messagingClientService.receive("email", (email) -> this.process((EmailDTO) email));
    }

    @Override
    public void process(EmailDTO emailDTO) {
        LOG.debug("Email send simulation - start");
        emailDTO.setFrom(sender);
        if (emailDTO.isHtml()) {
            try {
                LOG.debug("Email send simulation - html email");
                MimeMessage mimeMessage = this.prepareHtmlEmail(emailDTO);
                LOG.debug(emailDTO.toString());
                LOG.debug(mimeMessage.toString());
                String htmlContent = getTextFromMimeContent(mimeMessage.getContent());
                if (nonNull(htmlContent)) {
                    LOG.debug("Html content: ", "\n".concat(htmlContent).concat("\n"));
                }
            } catch (MessagingException e) {
                LOG.debug("Email send simulation - html email sending failed, attempting to send a simple email", e.getMessage());
                SimpleMailMessage simpleMailMessage = this.prepareSimpleEmail(emailDTO);
                LOG.debug(simpleMailMessage.toString());
            } catch (IOException e) {
                LOG.error("Error to getting html content from MimeMessage", e);
            }
        } else {
            LOG.debug("Email send simulation - simple email");
            SimpleMailMessage simpleMailMessage = this.prepareSimpleEmail(emailDTO);
            LOG.debug(simpleMailMessage.toString());
        }
        LOG.debug("Email send simulation - end");
    }

    private String getTextFromMimeContent(Object content) {
        try {
            requireNonNull(content);
            if (content instanceof String) {
                return (String) content;
            } else if (content instanceof Multipart multipart) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < multipart.getCount(); i++) {
                    sb.append(getTextFromMimeContent(multipart.getBodyPart(i).getContent()));
                }
                return sb.toString();
            } else {
                throw new RuntimeException(String.format("The content parameter must be of type 'String' or 'Multipart'. The type passed was '%s'", content.getClass().getSimpleName()));
            }
        } catch (Exception e) {
            LOG.debug("Error to getting html content", e);
        }
        return null;
    }
}
