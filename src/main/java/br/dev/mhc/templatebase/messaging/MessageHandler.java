package br.dev.mhc.templatebase.messaging;

@FunctionalInterface
public interface MessageHandler<T> {

    void handleMessage(T message);
}
