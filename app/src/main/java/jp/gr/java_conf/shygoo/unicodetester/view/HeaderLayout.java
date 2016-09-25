package jp.gr.java_conf.shygoo.unicodetester.view;

import android.content.Context;
import android.util.AttributeSet;

public class HeaderLayout extends AutoScrollLayout {

    public HeaderLayout(Context context) {
        super(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected float getLengthToShow() {
        return getHeight();
    }

    protected float getLengthToHide() {
        return 0f;
    }
}
