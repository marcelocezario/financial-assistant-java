package br.dev.mhc.financialassistant.messaging;

public interface IConsumerMessageService {

    void receive(String topic, MessageHandler<?> messageHandler);
}
