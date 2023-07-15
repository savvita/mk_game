package com.savita.machi_koro.events;

public interface ActionEvent<T> {
    void invoke(T arg);
}
