package jp.gr.java_conf.shygoo.unicodetester.view;

import android.content.Context;
import android.util.AttributeSet;

public class FooterLayout extends AutoScrollLayout {

    public FooterLayout(Context context) {
        super(context);
    }

    public FooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected float getLengthToShow() {
        return -getHeight();
    }

    protected float getLengthToHide() {
        return 0f;
    }
}
