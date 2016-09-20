package jp.gr.java_conf.shygoo.unicodetester;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AnalysisType {

    CODE_POINT(R.string.label_code_point),
    JAVA_CHAR(R.string.label_java_char),
    UTF8(R.string.label_utf8),
    UTF16_BE(R.string.label_utf16_be),
    UTF16_LE(R.string.label_utf16_le),
    UTF32_BE(R.string.label_utf32_be),
    UTF32_LE(R.string.label_utf32_le);

    @Getter
    private int label;
}
