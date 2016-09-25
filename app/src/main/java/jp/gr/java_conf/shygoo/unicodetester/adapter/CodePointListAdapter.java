package jp.gr.java_conf.shygoo.unicodetester.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.util.CodePointUtil;

public class CodePointListAdapter extends RecyclerView.Adapter<CodePointListAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_point_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int codePoint = position;
        holder.glyph.setText(CodePointUtil.toString(codePoint));
        holder.codePoint.setText(CodePointUtil.format(codePoint));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (context instanceof ClickListener) {
                    ((ClickListener) context).onCodePointClick(codePoint);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return CodePointUtil.COUNT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.glyph)
        TextView glyph;

        @BindView(R.id.code_point)
        TextView codePoint;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ClickListener {

        void onCodePointClick(int codePoint);
    }
}
