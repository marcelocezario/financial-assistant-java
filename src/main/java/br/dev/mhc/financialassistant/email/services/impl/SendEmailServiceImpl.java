package br.dev.mhc.financialassistant.email.services.impl;

import br.dev.mhc.financialassistant.email.EmailDTO;
import br.dev.mhc.financialassistant.email.services.interfaces.IProcessEmailSendingService;
import br.dev.mhc.financialassistant.email.services.interfaces.ISendEmailService;
import br.dev.mhc.financialassistant.messaging.IProducerMessageService;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements ISendEmailService {

    private final IProducerMessageService producerMessageService;

    public SendEmailServiceImpl(IProcessEmailSendingService processEmailSendingService, IProducerMessageService producerMessageService) {
        this.producerMessageService = producerMessageService;
    }

    @Override
    public void send(EmailDTO emailDTO) {
        producerMessageService.send("email", emailDTO);
    }
}
