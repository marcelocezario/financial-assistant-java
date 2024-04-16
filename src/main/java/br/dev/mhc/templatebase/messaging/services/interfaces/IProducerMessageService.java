package br.dev.mhc.templatebase.messaging.services.interfaces;

public interface IProducerMessageService {

    void send(String topic, Object message);
}
