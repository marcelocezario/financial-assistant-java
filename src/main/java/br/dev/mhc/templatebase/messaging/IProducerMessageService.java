package br.dev.mhc.templatebase.messaging;

public interface IProducerMessageService {

    void send(String topic, Object message);
}
