package jp.gr.java_conf.shygoo.unicodetester.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                EditText input = ButterKnife.findById((AlertDialog) dialog, R.id.input);
                inputListener.onCodePointInput(Integer.parseInt(input.getText().toString(), 16));
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
        EditText input = ButterKnife.findById(customView, R.id.input);
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
                positiveButton.setEnabled(CodePointUtil.isValidCodePoint(s));
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
