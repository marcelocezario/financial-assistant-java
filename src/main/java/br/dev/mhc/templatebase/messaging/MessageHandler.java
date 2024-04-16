package br.dev.mhc.templatebase.messaging;

@FunctionalInterface
public interface MessageHandler {

    void handleMessage(Object message);
}
