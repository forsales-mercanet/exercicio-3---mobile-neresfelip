package br.com.neresfelip.mercanet.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

public abstract class TaskRunner<T> {
    /** Criei esta classe que faz a mesma função da AsyncTask, já que ela foi descontinuada (deprecated) :( */

    private T object;

    public void executeAsync() {

        AsyncTask.execute(() -> {
            object = onBackground();
            new Handler(Looper.getMainLooper()).post(() -> onForeground(object));
        });

    }

    protected abstract T onBackground();
    protected abstract void onForeground(T object);

}
