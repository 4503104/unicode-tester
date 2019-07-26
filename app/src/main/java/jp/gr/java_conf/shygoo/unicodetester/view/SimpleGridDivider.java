package jp.gr.java_conf.shygoo.unicodetester.view;

import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import jp.gr.java_conf.shygoo.unicodetester.MyApp;

public class SimpleGridDivider extends RecyclerView.ItemDecoration {

    private final int spanCount;

    private final int dividerHalf;

    public SimpleGridDivider(int spanCount, @DimenRes int dividerResId) {
        this.spanCount = spanCount;
        float dividerSize = MyApp.getInstance().getResources().getDimension(dividerResId);
        dividerHalf = (int) (dividerSize / 2 + 0.5f);// at least 1px.
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (isFirstRow(position, spanCount)) {
            outRect.bottom = dividerHalf;
        } else if (isLastRow(position, spanCount, parent.getAdapter().getItemCount())) {
            outRect.top = dividerHalf;
        } else {
            outRect.top = dividerHalf;
            outRect.bottom = dividerHalf;
        }
        if (isFirstColumn(position, spanCount)) {
            outRect.right = dividerHalf;
        } else if (isLastColumn(position, spanCount)) {
            outRect.left = dividerHalf;
        } else {
            outRect.left = dividerHalf;
            outRect.right = dividerHalf;
        }
    }

    private boolean isFirstRow(int position, int spanCount) {
        return position < spanCount;
    }

    private boolean isLastRow(int position, int spanCount, int itemCount) {
        return position / spanCount == (itemCount - 1) / spanCount;
    }

    private boolean isFirstColumn(int position, int spanCount) {
        return position % spanCount == 0;
    }

    private boolean isLastColumn(int position, int spanCount) {
        return position % spanCount == spanCount - 1;
    }
}
