package jp.gr.java_conf.shygoo.unicodetester.view;

import android.content.Context;
import android.util.AttributeSet;

public class FooterBehavior extends AutoScrollBehavior {

    public FooterBehavior() {
        super();
    }

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollUp(AutoScrollLayout view) {
        view.show();
    }

    protected void onScrollDown(AutoScrollLayout view) {
        view.hide();
    }
}
