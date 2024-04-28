package br.dev.mhc.financialassistant.messaging;

@FunctionalInterface
public interface MessageHandler<T> {

    void handleMessage(T message);
}
