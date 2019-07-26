package jp.gr.java_conf.shygoo.unicodetester;

import android.app.Application;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

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
