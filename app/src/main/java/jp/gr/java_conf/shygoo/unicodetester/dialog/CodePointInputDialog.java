package jp.gr.java_conf.shygoo.unicodetester.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.util.CodePointUtil;

public class CodePointInputDialog extends DialogFragment {

    private InputListener inputListener;

    public static CodePointInputDialog newInstance() {
        return new CodePointInputDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getTargetFragment() instanceof InputListener) {
            inputListener = (InputListener) getTargetFragment();
        } else if (context instanceof InputListener) {
            inputListener = (InputListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use custom layout.
        Context context = getActivity();
        @SuppressLint("InflateParams")
        View customView = LayoutInflater.from(context).inflate(R.layout.code_point_input, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(R.string.message_input_code_point)
                .setView(customView);

        // Set ok/cancel button.
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (inputListener == null) {
                    return;
                }
                EditText input = customView.findViewById(R.id.input);
                inputListener.onCodePointInput(CodePointUtil.parseCodePoint(input.getText()));
            }
        }).setNegativeButton(android.R.string.cancel, null);

        // If input text is empty, disable ok button.
        Dialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });
        EditText input = customView.findViewById(R.id.input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AlertDialog dialog = (AlertDialog) getDialog();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                View preview = customView.findViewById(R.id.preview);
                if (CodePointUtil.isValidCodePoint(s)) {
                    preview.setVisibility(View.VISIBLE);
                    int codePoint = CodePointUtil.parseCodePoint(s);
                    TextView glyph = customView.findViewById(R.id.glyph);
                    glyph.setText(CodePointUtil.toString(codePoint));
                    TextView name = customView.findViewById(R.id.name);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        name.setText(CodePointUtil.nameOf(codePoint));
                    } else {
                        name.setVisibility(View.GONE);
                    }
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(false);
                    preview.setVisibility(View.GONE);
                }
            }
        });

        return dialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        inputListener = null;
    }

    public interface InputListener {

        void onCodePointInput(int codePoint);
    }
}
