package br.dev.mhc.templatebase.email.services.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.email.services.interfaces.IProcessEmailSendingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Locale;

public abstract class AbstractProcessEmailSendingServiceImpl implements IProcessEmailSendingService {

    private static final LogHelper LOG = new LogHelper(AbstractProcessEmailSendingServiceImpl.class);
    protected final TemplateEngine templateEngine;
    protected final MailSender mailSender;
    protected final JavaMailSender javaMailSender;
    @Value("${email.sender}")
    protected String sender;

    public AbstractProcessEmailSendingServiceImpl(TemplateEngine templateEngine, MailSender mailSender, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
    }

    protected SimpleMailMessage prepareSimpleEmail(EmailDTO email) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(email.getFrom());
        smm.setTo(email.getTo().toArray(new String[0]));
        smm.setCc(email.getCc().toArray(new String[0]));
        smm.setBcc(email.getBcc().toArray(new String[0]));
        smm.setSubject(email.getSubject());
        smm.setSentDate(Date.from(email.getSentDate()));
        smm.setText(email.getBody());
        return smm;
    }

    protected MimeMessage prepareHtmlEmail(EmailDTO email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setFrom(email.getFrom());
        mmh.setTo(email.getTo().toArray(new String[0]));
        mmh.setCc(email.getCc().toArray(new String[0]));
        mmh.setBcc(email.getBcc().toArray(new String[0]));
        mmh.setSubject(email.getSubject());
        mmh.setSentDate(Date.from(email.getSentDate()));
        mmh.setText(getHtmlFromTemplate(email), true);
        return mimeMessage;
    }

    protected String getHtmlFromTemplate(EmailDTO email) {
        Locale locale = LocaleContextHolder.getLocale();
        LOG.debug("Email send - html email locale", locale);
        Context context = new Context(locale);
        email.getHtmlAttributes().forEach(context::setVariable);
        return templateEngine.process(email.getPathHtmlTemplate(), context);
    }
}
