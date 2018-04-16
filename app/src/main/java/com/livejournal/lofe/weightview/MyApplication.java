package com.livejournal.lofe.weightview;

import android.app.Application;
import android.content.Context;

import static com.livejournal.lofe.weightview.MyUtil.log;

public class MyApplication extends Application {
    private static Context appContext;
    public static Context getContext() {
        //log("Контекст???");
        //log(appContext.toString());
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
