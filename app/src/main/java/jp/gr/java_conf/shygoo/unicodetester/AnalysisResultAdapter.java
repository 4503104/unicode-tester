package jp.gr.java_conf.shygoo.unicodetester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wefika.flowlayout.FlowLayout;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class AnalysisResultAdapter extends BaseExpandableListAdapter {

    @Setter
    private String targetText;

    @Override
    public int getGroupCount() {
        return AnalysisType.values().length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;// group : child = 1 : 1
    }

    @Override
    public AnalysisType getGroup(int groupPosition) {
        return AnalysisType.values()[groupPosition];
    }

    @Override
    public AnalysisType getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getGroupId(groupPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View view;
        GroupViewHolder holder;
        Context context = parent.getContext();
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.analysis_result_label, parent, false);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (GroupViewHolder) view.getTag();
        }

        AnalysisType type = getGroup(groupPosition);

        holder.label.setText(context.getString(type.getLabel()));
        int indicatorResId = isExpanded ? R.drawable.ic_expand_less_gray_24dp : R.drawable.ic_expand_more_gray_24dp;
        holder.indicator.setImageResource(indicatorResId);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View view;
        ChildViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_result_data, parent, false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }

        AnalysisType type = getChild(groupPosition, childPosition);
        analyze(type, holder);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private synchronized void analyze(AnalysisType type, ChildViewHolder holder) {

        String targetText = this.targetText;
        if (targetText == null) {
            targetText = "";
        }

        List<CharSequence> detailValues = null;
        int size = 0;
        switch (type) {
            case CODE_POINT:
                int[] codePoints = getCodePoints(targetText);
                detailValues = formatCodePoints(codePoints);
                size = codePoints.length;
                break;
            case JAVA_CHAR:
                char[] chars = targetText.toCharArray();
                detailValues = formatChars(chars);
                size = chars.length;
                break;
            case UTF_8:
            case UTF_16BE:
            case UTF_16LE:
            case UTF_32BE:
            case UTF_32LE:
                Charset charset = Charset.forName(type.getCharsetName());
                detailValues = formatBytes(targetText, charset);
                size = targetText.getBytes(charset).length;
                break;
        }
        Context context = holder.size.getContext();
        CharSequence formattedSize = formatSize(context, type, size);
        holder.update(detailValues, formattedSize);
    }

    private int[] getCodePoints(String targetText) {
        int len = targetText.length();
        int[] codePoints = new int[targetText.codePointCount(0, len)];
        for (int i = 0, j = 0; i < len; i = targetText.offsetByCodePoints(i, 1), j++) {
            codePoints[j] = targetText.codePointAt(i);
        }
        return codePoints;
    }

    private List<CharSequence> formatCodePoints(int[] codePoints) {
        List<CharSequence> formattedValues = new ArrayList<>();
        for (int codePoint : codePoints) {
            formattedValues.add(String.format(Locale.getDefault(), "U+%04X", codePoint));
        }
        return formattedValues;
    }

    private List<CharSequence> formatChars(char[] chars) {
        List<CharSequence> formattedValues = new ArrayList<>();
        for (char c : chars) {
            formattedValues.add(String.format(Locale.getDefault(), "\\u%04X", (int) c));
        }
        return formattedValues;
    }

    private List<CharSequence> formatBytes(String targetText, Charset charset) {
        int[] codePoints = getCodePoints(targetText);
        List<CharSequence> formattedValues = new ArrayList<>();
        for (int i = 0, len = codePoints.length; i < len; i++) {
            String str = new String(codePoints, i, 1);
            byte[] bytes = str.getBytes(charset);
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format(Locale.getDefault(), " %02X", b));
            }
            formattedValues.add(sb.substring(1));
        }
        return formattedValues;
    }

    private CharSequence formatSize(Context context, AnalysisType type, int size) {
        return context.getResources().getQuantityString(type.getSizeFormat(), size, size);
    }

    static class GroupViewHolder {

        @BindView(R.id.label)
        TextView label;

        @BindView(R.id.indicator)
        ImageView indicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {

        @BindView(R.id.data_detail)
        FlowLayout detail;

        @BindView(R.id.data_size)
        TextView size;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void update(List<CharSequence> detailValues, CharSequence sizeValue) {

            // Update size;
            size.setText(sizeValue);

            // Update detail.
            if (detailValues == null) {
                detail.removeAllViews();
                return;
            }
            int valueCount = detailValues.size();
            int viewCount = detail.getChildCount();
            int diff = valueCount - viewCount;
            if (diff > 0) {
                LayoutInflater inflater = LayoutInflater.from(detail.getContext());
                for (int i = 0; i < diff; i++) {
                    detail.addView(inflater.inflate(R.layout.data_balloon, detail, false));
                }
            } else if (diff < 0) {
                for (int i = diff; i < 0; i++) {
                    detail.removeViewAt(valueCount);
                }
            }
            for (int i = 0; i < valueCount; i++) {
                ((TextView) detail.getChildAt(i)).setText(detailValues.get(i));
            }
        }
    }
}
