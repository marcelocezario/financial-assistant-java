package br.dev.mhc.templatebase.messaging.client.interfaces;

import br.dev.mhc.templatebase.messaging.MessageHandler;

public interface IMessagingClientService {
    void send(String topic, Object message);

    void receive(String topic, MessageHandler messageHandler);
}
