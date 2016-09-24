package jp.gr.java_conf.shygoo.unicodetester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.gr.java_conf.shygoo.unicodetester.R;
import jp.gr.java_conf.shygoo.unicodetester.util.CodePointUtil;

public class CodePointListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return CodePointUtil.COUNT;
    }

    @Override
    public Integer getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_point_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final int codePoint = getItem(position);
        holder.glyph.setText(CodePointUtil.toString(codePoint));
        holder.codePoint.setText(CodePointUtil.format(codePoint));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (context instanceof ClickListener) {
                    ((ClickListener) context).onCodePointClick(codePoint);
                }
            }
        });

        return view;
    }

    static class ViewHolder {

        @BindView(R.id.glyph)
        TextView glyph;

        @BindView(R.id.code_point)
        TextView codePoint;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface ClickListener {

        void onCodePointClick(int codePoint);
    }
}
