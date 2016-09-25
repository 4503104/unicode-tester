package jp.gr.java_conf.shygoo.unicodetester.view;

import android.content.Context;
import android.util.AttributeSet;

public class HeaderBehavior extends AutoScrollBehavior {

    public HeaderBehavior() {
        super();
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollUp(AutoScrollLayout view) {
        view.hide();
    }

    protected void onScrollDown(AutoScrollLayout view) {
        view.show();
    }
}
