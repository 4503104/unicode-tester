package jp.gr.java_conf.shygoo.unicodetester.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.adapter.CodePointListAdapter;
import jp.gr.java_conf.shygoo.unicodetester.dialog.CodePointDetailDialog;
import jp.gr.java_conf.shygoo.unicodetester.dialog.CodePointInputDialog;

public class CodePointListActivity extends AppCompatActivity implements CodePointListAdapter.ClickListener,
        CodePointDetailDialog.SelectListener, CodePointInputDialog.InputListener {

    public static final String EXTRA_NAME_CODE_POINT = "codePoint";

    private static final String REQUEST_TAG_JUMP = "jump";

    private static final String REQUEST_TAG_VIEW = "view";

    @BindView(R.id.code_point_list)
    GridView codePointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_point_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        codePointList.setAdapter(new CodePointListAdapter());
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
        codePointList.setSelection(codePoint);
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
}
