package jp.gr.java_conf.shygoo.unicodetester;

import android.app.Application;

import lombok.Getter;

public class MyApp extends Application {

    @Getter
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
