package br.dev.mhc.templatebase.messaging.services.impl;

import br.dev.mhc.templatebase.messaging.client.interfaces.IMessagingClientService;
import br.dev.mhc.templatebase.messaging.services.interfaces.IProducerMessageService;
import org.springframework.stereotype.Service;

@Service
public class ProducerMessageServiceImpl implements IProducerMessageService {

    private final IMessagingClientService messagingClientService;

    public ProducerMessageServiceImpl(IMessagingClientService messagingClientService) {
        this.messagingClientService = messagingClientService;
    }

    @Override
    public void send(String topic, Object message) {
        messagingClientService.send(topic, message);
    }
}
