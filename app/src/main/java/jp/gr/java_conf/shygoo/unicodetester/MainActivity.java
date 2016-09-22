package jp.gr.java_conf.shygoo.unicodetester;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements CodePointInputDialog.InputListener {

    @BindView(R.id.main_text)
    EditText mainText;

    @BindView(R.id.clear_button)
    View clearButton;

    @BindView(R.id.analysis_results)
    ExpandableListView analysisResults;

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
            case R.id.input:
                requestInput();
                return true;
            case R.id.select:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.clear_button)
    public void clearText() {
        mainText.setText(null);
    }

    private void requestInput() {
        DialogFragment fragment = CodePointInputDialog.newInstance();
        fragment.show(getSupportFragmentManager(), "input");
    }

    @Override
    public void onCodePointInput(int codePoint) {
        mainText.append(String.valueOf(Character.toChars(codePoint)));
    }
}
