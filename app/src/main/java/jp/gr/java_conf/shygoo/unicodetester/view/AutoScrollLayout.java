package jp.gr.java_conf.shygoo.unicodetester.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class AutoScrollLayout extends LinearLayout {

    private final long ANIMATION_DURATION_MSEC = 200L;

    private final long AUTO_HIDE_DURATION_MSEC = 2000L;

    private Handler handler;

    private Runnable autoHideTask;

    private Animator.AnimatorListener showingAnimationListener;

    public AutoScrollLayout(Context context) {
        super(context);
        init();
    }

    public AutoScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        handler = new Handler(Looper.getMainLooper());
        autoHideTask = new Runnable() {
            @Override
            public void run() {
                hide();
            }
        };
        showingAnimationListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // nop
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startTimerToHide();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // nop
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // nop
            }
        };
    }

    public synchronized void show() {
        clearAnimation();
        clearTimerToHide();
        animate().translationY(getLengthToShow())
                .setDuration(ANIMATION_DURATION_MSEC)
                .setListener(showingAnimationListener);
    }

    public synchronized void hide() {
        clearAnimation();
        animate().translationY(getLengthToHide())
                .setDuration(ANIMATION_DURATION_MSEC);
    }

    protected abstract float getLengthToShow();

    protected abstract float getLengthToHide();

    public synchronized void resetTimerToHide() {
        clearTimerToHide();
        startTimerToHide();
    }

    private synchronized void startTimerToHide() {
        handler.postDelayed(autoHideTask, AUTO_HIDE_DURATION_MSEC);
    }

    private synchronized void clearTimerToHide() {
        handler.removeCallbacks(autoHideTask);
    }
}
