package jp.gr.java_conf.shygoo.unicodetester.util;

import android.content.Context;
import android.content.SharedPreferences;

import jp.gr.java_conf.shygoo.unicodetester.MyApp;

public class SharedPreferencesUtil {

    private static final String SHARED_PREFERENCES_NAME = "saveData";

    private static final String KEY_SCROLL_POSITION = "scrollPosition";

    private static SharedPreferences getPreferences() {
        return MyApp.getInstance().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void saveScrollPosition(int scrollPosition) {
        getPreferences().edit().putInt(KEY_SCROLL_POSITION, scrollPosition).apply();
    }

    public static int loadScrollPosition() {
        return getPreferences().getInt(KEY_SCROLL_POSITION, 0);
    }
}
