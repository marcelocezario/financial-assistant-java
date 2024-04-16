package br.dev.mhc.templatebase.messaging.client.impl;

import br.dev.mhc.templatebase.messaging.MessageHandler;
import br.dev.mhc.templatebase.messaging.client.interfaces.IMessagingClientService;
import org.springframework.stereotype.Service;

@Service
public class MockMessagingClientServiceImpl implements IMessagingClientService {
    @Override
    public void send(String topic, Object message) {

    }

    @Override
    public void receive(String topic, MessageHandler messageHandler) {

    }
}
