package jp.gr.java_conf.shygoo.unicodetester.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.util.CodePointUtil;

public class CodePointDetailDialog extends DialogFragment {

    private static final String ARG_CODE_POINT = "codePoint";

    private int codePoint;

    private SelectListener selectListener;

    public static CodePointDetailDialog newInstance(int codePoint) {
        CodePointDetailDialog dialog = new CodePointDetailDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_CODE_POINT, codePoint);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getTargetFragment() instanceof SelectListener) {
            selectListener = (SelectListener) getTargetFragment();
        } else if (context instanceof SelectListener) {
            selectListener = (SelectListener) getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        codePoint = getArguments().getInt(ARG_CODE_POINT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use custom layout.
        Context context = getActivity();
        @SuppressLint("InflateParams")
        View customView = LayoutInflater.from(context).inflate(R.layout.code_point_detail, null);

        // Set codePoint info.
        TextView glyph = customView.findViewById(R.id.glyph);
        glyph.setText(CodePointUtil.toString(codePoint));

        TextView codePointValue = customView.findViewById(R.id.code_point_value);
        codePointValue.setText(CodePointUtil.formatLong(codePoint));

        TextView categoryValue = customView.findViewById(R.id.category_value);
        categoryValue.setText(CodePointUtil.categoryOf(codePoint));

        TextView scriptLabel = customView.findViewById(R.id.script_label);
        TextView scriptValue = customView.findViewById(R.id.script_value);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            scriptValue.setText(CodePointUtil.scriptOf(codePoint));
        } else {
            scriptLabel.setVisibility(View.GONE);
            scriptValue.setVisibility(View.GONE);
        }

        TextView blockValue = customView.findViewById(R.id.block_value);
        blockValue.setText(CodePointUtil.blockOf(codePoint));

        TextView nameLabel = customView.findViewById(R.id.name_label);
        TextView nameValue = customView.findViewById(R.id.name_value);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            nameValue.setText(CodePointUtil.nameOf(codePoint));
        } else {
            nameLabel.setVisibility(View.GONE);
            nameValue.setVisibility(View.GONE);
        }

        TextView directionalityValue = customView.findViewById(R.id.directionality_value);
        directionalityValue.setText(CodePointUtil.directionalityOf(codePoint));

        // Create dialog.
        return new AlertDialog.Builder(context)
                .setView(customView)
                .setMessage(R.string.message_confirm_code_point)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (selectListener != null) {
                            selectListener.onCodePointSelect(codePoint);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        selectListener = null;
    }

    public interface SelectListener {

        void onCodePointSelect(int codePoint);
    }
}
