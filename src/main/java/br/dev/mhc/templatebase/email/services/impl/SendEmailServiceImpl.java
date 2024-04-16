package br.dev.mhc.templatebase.email.services.impl;

import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.email.services.interfaces.IProcessEmailSendingService;
import br.dev.mhc.templatebase.email.services.interfaces.ISendEmailService;
import br.dev.mhc.templatebase.messaging.services.interfaces.IProducerMessageService;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements ISendEmailService {

    private final IProducerMessageService sendMessageService;

    public SendEmailServiceImpl(IProcessEmailSendingService processEmailSendingService, IProducerMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void send(EmailDTO emailDTO) {
        sendMessageService.send("email", emailDTO);
    }
}
