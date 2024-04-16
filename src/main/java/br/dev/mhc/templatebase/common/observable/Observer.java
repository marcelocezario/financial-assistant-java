package br.dev.mhc.templatebase.common.observable;

@FunctionalInterface
public interface Observer {
    void update(Object object);
}
