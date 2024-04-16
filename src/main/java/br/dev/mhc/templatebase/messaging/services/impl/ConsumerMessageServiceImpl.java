package br.dev.mhc.templatebase.messaging.services.impl;

import br.dev.mhc.templatebase.messaging.MessageHandler;
import br.dev.mhc.templatebase.messaging.client.interfaces.IMessagingClientService;
import br.dev.mhc.templatebase.messaging.services.interfaces.IConsumerMessageService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerMessageServiceImpl implements IConsumerMessageService {

    private final IMessagingClientService messagingClientService;

    public ConsumerMessageServiceImpl(IMessagingClientService messagingClientService) {
        this.messagingClientService = messagingClientService;
    }

    @Override
    public void receive(String topic, MessageHandler messageHandler) {
        messagingClientService.receive(topic, messageHandler);
    }
}
