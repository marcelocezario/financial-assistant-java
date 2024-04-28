package br.dev.mhc.financialassistant.messaging;

public interface IProducerMessageService {

    void send(String topic, Object message);
}
