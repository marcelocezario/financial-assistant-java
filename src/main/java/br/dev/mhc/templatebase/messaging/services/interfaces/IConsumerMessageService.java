package br.dev.mhc.templatebase.messaging.services.interfaces;

import br.dev.mhc.templatebase.messaging.MessageHandler;

public interface IConsumerMessageService {

    void receive(String topic, MessageHandler messageHandler);
}
