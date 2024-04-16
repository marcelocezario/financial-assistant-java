package br.dev.mhc.templatebase.messaging;

public interface IConsumerMessageService {

    void receive(String topic, MessageHandler<Object> messageHandler);
}
