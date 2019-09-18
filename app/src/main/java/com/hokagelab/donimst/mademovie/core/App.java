package com.hokagelab.donimst.mademovie.core;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App appInstance = null;
    private static Context appContext;

    public static App getInstance() {
        return appInstance;
    }

    public static App get() {
        return get(appContext);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        appContext = getApplicationContext();
    }

//    @Override
//    public void onConfigurationChanged(Configuration config) {
//        super.onConfigurationChanged(config);
//    }
}
