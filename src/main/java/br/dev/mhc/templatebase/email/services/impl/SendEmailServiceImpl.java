package br.dev.mhc.templatebase.email.services.impl;

import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.email.services.interfaces.IProcessEmailSendingService;
import br.dev.mhc.templatebase.email.services.interfaces.ISendEmailService;
import br.dev.mhc.templatebase.messaging.IProducerMessageService;
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
        producerMessageService.send("teste", "APENAS TESTANDO ESSA BUDEGA");
        producerMessageService.send("outroTeste", "FAZENDO OUTRO TESTE");
        producerMessageService.send("outroTeste", emailDTO.toString());
    }
}
