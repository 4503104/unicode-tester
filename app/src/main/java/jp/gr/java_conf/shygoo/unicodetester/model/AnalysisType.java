package jp.gr.java_conf.shygoo.unicodetester.model;

import jp.gr.java_conf.shygoo.unicodetester.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AnalysisType {

    CODE_POINT(null, R.string.label_code_point, R.plurals.size_format_code_point),
    JAVA_CHAR(null, R.string.label_java_char, R.plurals.size_format_java_char),
    UTF_8("UTF-8", R.string.label_utf_8, R.plurals.size_format_byte),
    UTF_16BE("UTF-16BE", R.string.label_utf_16be, R.plurals.size_format_byte),
    UTF_16LE("UTF-16LE", R.string.label_utf_16le, R.plurals.size_format_byte),
    UTF_32BE("UTF-32BE", R.string.label_utf_32be, R.plurals.size_format_byte),
    UTF_32LE("UTF-32LE", R.string.label_utf_32le, R.plurals.size_format_byte);

    @Getter
    private String charsetName;

    @Getter
    private int label;

    @Getter
    private int sizeFormat;
}
