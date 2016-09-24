package jp.gr.java_conf.shygoo.unicodetester.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.regex.Pattern;

public class CodePointUtil {

    public static final int COUNT = Character.MAX_CODE_POINT + 1;

    private static final String FORMAT = "U+%04X";

    private static final String FORMAT_WITH_SURROGATE_PAIR = "U+%1$05X (U+%2$04X, U+%3$04X)";

    private static final Pattern PATTERN_CODE_POINT = Pattern.compile("^[0-9a-fA-F]{1,6}$");

    public static boolean isValidCodePoint(CharSequence input) {
        if (PATTERN_CODE_POINT.matcher(input).matches()) {
            int codePoint = Integer.parseInt(input.toString(), 16);
            return codePoint <= Character.MAX_CODE_POINT;
        }
        return false;
    }

    public static String toString(int codePoint) {
        return new String(Character.toChars(codePoint));
    }

    public static String format(int codePoint) {
        return String.format(FORMAT, codePoint);
    }

    public static String formatLong(int codePoint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Character.isSupplementaryCodePoint(codePoint)) {
            int highSurrogate = Character.highSurrogate(codePoint);
            int lowSurrogate = Character.lowSurrogate(codePoint);
            return String.format(FORMAT_WITH_SURROGATE_PAIR, codePoint, highSurrogate, lowSurrogate);
        } else {
            return format(codePoint);
        }
    }

    public static String categoryOf(int codePoint) {
        switch (Character.getType(codePoint)) {
            case Character.UNASSIGNED:
                return "[Cn] Other, not assigned";
            case Character.UPPERCASE_LETTER:
                return "[Lu] Letter, uppercase";
            case Character.LOWERCASE_LETTER:
                return "[Ll] Letter, lowercase";
            case Character.TITLECASE_LETTER:
                return "[Lt] Letter, titlecase";
            case Character.MODIFIER_LETTER:
                return "[Lm] Letter, modifier";
            case Character.OTHER_LETTER:
                return "[Lo] Letter, other";
            case Character.NON_SPACING_MARK:
                return "[Mn] Mark, nonspacing";
            case Character.ENCLOSING_MARK:
                return "[Me] Mark, enclosing";
            case Character.COMBINING_SPACING_MARK:
                return "[Mc] Mark, spacing combining";
            case Character.DECIMAL_DIGIT_NUMBER:
                return "[Nd] Number, decimal digit";
            case Character.LETTER_NUMBER:
                return "[Nl] Number, letter";
            case Character.OTHER_NUMBER:
                return "[No] Number, other";
            case Character.SPACE_SEPARATOR:
                return "[Zs] Separator, space";
            case Character.LINE_SEPARATOR:
                return "[Zl] Separator, line";
            case Character.PARAGRAPH_SEPARATOR:
                return "[Zp] Separator, paragraph";
            case Character.CONTROL:
                return "[Cc] Other, control";
            case Character.FORMAT:
                return "[Cf] Other, format";
            case Character.PRIVATE_USE:
                return "[Co] Other, private use";
            case Character.SURROGATE:
                return "[Cs] Other, surrogate";
            case Character.DASH_PUNCTUATION:
                return "[Pd] Punctuation, dash";
            case Character.START_PUNCTUATION:
                return "[Ps] Punctuation, open";
            case Character.END_PUNCTUATION:
                return "[Pe] Punctuation, close";
            case Character.CONNECTOR_PUNCTUATION:
                return "[Pc] Punctuation, connector";
            case Character.OTHER_PUNCTUATION:
                return "[Po] Punctuation, other";
            case Character.MATH_SYMBOL:
                return "[Sm] Symbol, math";
            case Character.CURRENCY_SYMBOL:
                return "[Sc] Symbol, currency";
            case Character.MODIFIER_SYMBOL:
                return "[Sk] Symbol, modifier";
            case Character.OTHER_SYMBOL:
                return "[So] Symbol, other";
            case Character.INITIAL_QUOTE_PUNCTUATION:
                return "[Pi] Punctuation, initial quote";
            case Character.FINAL_QUOTE_PUNCTUATION:
                return "[Pf] Punctuation, final quote";
            default:
                return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String scriptOf(int codePoint) {
        Character.UnicodeScript unicodeScript = Character.UnicodeScript.of(codePoint);
        if (unicodeScript == null) {
            return "";
        }
        return unicodeScript.toString();
    }

    public static String blockOf(int codePoint) {
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(codePoint);
        if (unicodeBlock == null) {
            return "";
        }
        return unicodeBlock.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String nameOf(int codePoint) {
        return Character.getName(codePoint);
    }

    public static String directionalityOf(int codePoint) {
        switch (Character.getDirectionality(codePoint)) {
            case Character.DIRECTIONALITY_UNDEFINED:
                return "Undefined";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT:
                return "[L] Strong, Left-to-Right";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT:
                return "[R] Strong, Right-to-Left";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC:
                return "[AL] Strong, Right-to-Left Arabic";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER:
                return "[EN] Weak, European Number";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR:
                return "[ES] Weak, European Number Separator";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR:
                return "[ET] Weak, European Number Terminator";
            case Character.DIRECTIONALITY_ARABIC_NUMBER:
                return "[AN] Weak, Arabic Number";
            case Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR:
                return "[CS] Weak, Common Number Separator";
            case Character.DIRECTIONALITY_NONSPACING_MARK:
                return "[NSM] Weak, Nonspacing Mark";
            case Character.DIRECTIONALITY_BOUNDARY_NEUTRAL:
                return "[BN] Weak, Boundary Neutral";
            case Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR:
                return "[B] Neutral, Paragraph Separator";
            case Character.DIRECTIONALITY_SEGMENT_SEPARATOR:
                return "[S] Neutral, Segment Separator";
            case Character.DIRECTIONALITY_WHITESPACE:
                return "[WS] Neutral, Whitespace";
            case Character.DIRECTIONALITY_OTHER_NEUTRALS:
                return "[ON] Neutral, Other Neutrals";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING:
                return "[LRE] Explicit Formatting, Left-to-Right Embedding";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE:
                return "[LRO] Explicit Formatting, Left-to-Right Override";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING:
                return "[RLE] Explicit Formatting, Right-to-Left Embedding";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE:
                return "[RLO] Explicit Formatting, Right-to-Left Override";
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT:
                return "[PDF] Explicit Formatting, Pop Directional Format";
            default:
                return "";
        }
    }
}
