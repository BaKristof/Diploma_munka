package com.example.myapplication;

@FunctionalInterface
public interface FunctionWithParams<T, R> {
    R apply(T t);
}
