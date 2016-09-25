package jp.gr.java_conf.shygoo.unicodetester.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public abstract class AutoScrollBehavior extends AppBarLayout.ScrollingViewBehavior {

    public AutoScrollBehavior() {
        super();
    }

    public AutoScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0) {
            onScrollUp((AutoScrollLayout) child);
        } else if (dyConsumed < 0) {
            onScrollDown((AutoScrollLayout) child);
        }
    }

    protected abstract void onScrollUp(AutoScrollLayout view);

    protected abstract void onScrollDown(AutoScrollLayout view);
}
