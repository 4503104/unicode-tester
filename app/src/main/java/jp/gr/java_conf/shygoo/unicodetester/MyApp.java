package jp.gr.java_conf.shygoo.unicodetester;

import android.app.Application;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;

import lombok.Getter;

public class MyApp extends Application {

    @Getter
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
    }
}
