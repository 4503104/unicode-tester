package jp.gr.java_conf.shygoo.unicodetester.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.adapter.AnalysisResultAdapter;
import jp.gr.java_conf.shygoo.unicodetester.dialog.CodePointInputDialog;
import jp.gr.java_conf.shygoo.unicodetester.util.CodePointUtil;

import static jp.gr.java_conf.shygoo.unicodetester.activity.CodePointListActivity.EXTRA_NAME_CODE_POINT;

public class MainActivity extends AppCompatActivity implements CodePointInputDialog.InputListener {

    @BindView(R.id.main_text)
    EditText mainText;

    @BindView(R.id.clear_button)
    View clearButton;

    @BindView(R.id.analysis_results)
    ExpandableListView analysisResults;

    private static final int REQUEST_CODE_SELECT = 100;

    private static final String REQUEST_TAG_INPUT = "input";

    private AnalysisResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new AnalysisResultAdapter();
        analysisResults.setAdapter(adapter);
        clearButton.setVisibility(View.INVISIBLE);
        mainText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nop
            }

            @Override
            public void afterTextChanged(Editable s) {
                clearButton.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
                adapter.setTargetText(s.toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select:
                requestSelect();
                return true;
            case R.id.input:
                requestInput();
                return true;
            case R.id.share:
                shareText();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.clear_button)
    public void clearText() {
        mainText.setText(null);
    }

    private void requestSelect() {
        Intent intent = new Intent(this, CodePointListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SELECT:
                if (resultCode == RESULT_OK) {
                    onCodePointInput(data.getIntExtra(EXTRA_NAME_CODE_POINT, 0));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void requestInput() {
        CodePointInputDialog.newInstance().show(getSupportFragmentManager(), REQUEST_TAG_INPUT);
    }

    @Override
    public void onCodePointInput(int codePoint) {
        String input = CodePointUtil.toString(codePoint);
        int selectionStart = mainText.getSelectionStart();
        if (selectionStart >= 0) {
            int selectionEnd = mainText.getSelectionEnd();
            int replaceStart = Math.min(selectionStart, selectionEnd);
            int replaceEnd = Math.max(selectionStart, selectionEnd);
            Editable currentValue = mainText.getText();
            mainText.setText(currentValue.replace(replaceStart, replaceEnd, input));
            mainText.setSelection(replaceStart + input.length());
        } else {
            mainText.append(input);
            mainText.setSelection(mainText.getText().length());
        }
    }

    private void shareText() {
        CharSequence shareText = mainText.getText();
        if (TextUtils.isEmpty(shareText)) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
        intent.setType("text/plain");
        startActivity(intent);
    }
}
