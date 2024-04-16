package br.dev.mhc.templatebase.common.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private final List<Observer> observers = new ArrayList<>();

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void next(Object object) {
        observers.forEach(o -> o.update(object));
    }
}
