package com.example.spr_ypt.crazycodes.thread;


import com.example.spr_ypt.crazycodes.thread.synctask.TaskCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class MoliveThread<T> implements Runnable {
    protected List<TaskCallback<T>> callbacks = new ArrayList<TaskCallback<T>>();
    boolean finish = false;

    public MoliveThread(TaskCallback<T> callback) {
        this();
        addCallback(callback);
    }

    public MoliveThread() {
    }

    @Override
    public abstract void run();

    public void addCallback(TaskCallback<T> callback) {
        callbacks.add(callback);
    }


    public void callBack(T t) {
        finish = true;
        try {
            for (TaskCallback<T> callback : callbacks) {
                callback.callback(t);
            }
        } catch (OutOfMemoryError e) {
            for (TaskCallback<T> callback : callbacks) {
                callback.callback(null);
            }
        }
    }

    public boolean isFinish() {
        return finish;
    }

    public abstract void execute();
}
