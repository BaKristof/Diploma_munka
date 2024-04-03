package com.example.myapplication;

public class RunnableWithParams implements Runnable {

    private final FunctionWithParams<Object, Void> function;

    public RunnableWithParams(FunctionWithParams<Object, Void> function) {
        this.function = function;
    }

    @Override
    public void run() {
        function.apply(null);
    }
}