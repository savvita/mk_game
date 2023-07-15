package com.savita.machi_koro.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

public class Event<T> {
    private Collection<ActionEvent<T>> listeners = new ArrayList<>();
    public void add(ActionEvent<T> listener) {
        listeners.add(listener);
    }
    public void remove(ActionEvent<T> listener) {
        listeners.remove(listener);
    }
    public void invoke(T arg) {
        for(ActionEvent<T> listener : listeners) {
            listener.invoke(arg);
        }
    }
}
