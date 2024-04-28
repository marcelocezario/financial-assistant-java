package br.dev.mhc.financialassistant.common.observable;

@FunctionalInterface
public interface Observer {
    void update(Object object);
}
