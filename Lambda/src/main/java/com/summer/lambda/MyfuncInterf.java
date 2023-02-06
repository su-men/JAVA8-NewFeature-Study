package com.summer.lambda;

@FunctionalInterface
public interface MyfuncInterf<T> {
    void printMessage(T message);
}
