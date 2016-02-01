package com.selectphoto;

import android.app.Application;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }
    private static BaseApplication Instance;

    public static BaseApplication getInstance() {
        return Instance;
    }
}
