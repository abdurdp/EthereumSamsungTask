package com.abdur.ethereumsamsungtask.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;

import com.abdur.ethereumsamsungtask.BuildConfig;
import com.abdur.ethereumsamsungtask.data.source.APIClient;

import timber.log.Timber;

public class EthereumSamsungTaskApp extends Application implements LifecycleObserver {
    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        APIClient.init();
    }


    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(@NonNull StackTraceElement element) {
                    return super.createStackElementTag(element) + ": " + element.getLineNumber();
                }
            });
        }
    }
}
