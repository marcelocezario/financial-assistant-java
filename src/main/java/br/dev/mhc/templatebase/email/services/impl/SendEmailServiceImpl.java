package br.dev.mhc.templatebase.email.services.impl;

import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.email.services.interfaces.IProcessEmailSendingService;
import br.dev.mhc.templatebase.email.services.interfaces.ISendEmailService;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements ISendEmailService {

    private final IProcessEmailSendingService processEmailSendingService;

    public SendEmailServiceImpl(IProcessEmailSendingService processEmailSendingService) {
        this.processEmailSendingService = processEmailSendingService;
    }

    @Override
    public void send(EmailDTO emailDTO) {
        processEmailSendingService.process(emailDTO);
    }
}
