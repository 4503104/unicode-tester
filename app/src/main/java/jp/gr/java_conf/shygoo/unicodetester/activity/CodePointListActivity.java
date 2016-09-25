package jp.gr.java_conf.shygoo.unicodetester.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.adapter.CodePointListAdapter;
import jp.gr.java_conf.shygoo.unicodetester.dialog.CodePointDetailDialog;
import jp.gr.java_conf.shygoo.unicodetester.dialog.CodePointInputDialog;
import jp.gr.java_conf.shygoo.unicodetester.util.CodePointUtil;
import jp.gr.java_conf.shygoo.unicodetester.util.SharedPreferencesUtil;
import jp.gr.java_conf.shygoo.unicodetester.view.FooterLayout;
import jp.gr.java_conf.shygoo.unicodetester.view.HeaderLayout;

public class CodePointListActivity extends AppCompatActivity implements CodePointListAdapter.ClickListener,
        CodePointDetailDialog.SelectListener, CodePointInputDialog.InputListener {

    public static final String EXTRA_NAME_CODE_POINT = "codePoint";

    private static final int SPAN_COUNT = 4;

    private static final String REQUEST_TAG_JUMP = "jump";

    private static final String REQUEST_TAG_VIEW = "view";

    private GridLayoutManager layoutManager;

    @BindView(R.id.code_point_list)
    RecyclerView codePointList;

    @BindView(R.id.header)
    HeaderLayout header;

    @BindView(R.id.footer)
    FooterLayout footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_point_list);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        codePointList.setLayoutManager(layoutManager);
        codePointList.setAdapter(new CodePointListAdapter());
        codePointList.scrollToPosition(SharedPreferencesUtil.loadScrollPosition());
    }

    @OnClick(R.id.scroll_to_top)
    public void scrollToTop() {
        header.resetTimerToHide();
        codePointList.scrollToPosition(CodePointUtil.MIN);
    }

    @OnClick(R.id.scroll_up_long)
    public void scrollUpLong() {
        scrollUp(0x10000);
    }

    @OnClick(R.id.scroll_up_middle)
    public void scrollUpMiddle() {
        scrollUp(0x1000);
    }

    @OnClick(R.id.scroll_up_short)
    public void scrollUpShort() {
        scrollUp(0x100);
    }

    @OnClick(R.id.scroll_down_short)
    public void scrollDownShort() {
        scrollDown(0x100);
    }

    @OnClick(R.id.scroll_down_middle)
    public void scrollDownMiddle() {
        scrollDown(0x1000);
    }

    @OnClick(R.id.scroll_down_long)
    public void scrollDownLong() {
        scrollDown(0x10000);
    }

    @OnClick(R.id.scroll_to_bottom)
    public void scrollToBottom() {
        footer.resetTimerToHide();
        codePointList.scrollToPosition(CodePointUtil.MAX);
    }

    private void scrollUp(int length) {
        header.resetTimerToHide();
        codePointList.scrollToPosition(CodePointUtil.subtract(getCurrentPosition(), length));
    }

    private void scrollDown(int length) {
        footer.resetTimerToHide();
        codePointList.scrollToPosition(CodePointUtil.add(getCurrentPosition(), length));
    }

    private int getCurrentPosition() {
        return layoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_code_point_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case R.id.jump:
                requestJump();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void requestJump() {
        CodePointInputDialog.newInstance().show(getSupportFragmentManager(), REQUEST_TAG_JUMP);
    }

    @Override
    public void onCodePointInput(int codePoint) {
        codePointList.scrollToPosition(codePoint);
    }

    @Override
    public void onCodePointClick(int codePoint) {
        CodePointDetailDialog.newInstance(codePoint).show(getSupportFragmentManager(), REQUEST_TAG_VIEW);
    }

    @Override
    public void onCodePointSelect(int codePoint) {
        Intent result = new Intent();
        result.putExtra(EXTRA_NAME_CODE_POINT, codePoint);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void finish() {
        SharedPreferencesUtil.saveScrollPosition(getCurrentPosition());
        super.finish();
    }
}
