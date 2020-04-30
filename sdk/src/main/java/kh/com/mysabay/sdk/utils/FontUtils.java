package kh.com.mysabay.sdk.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.text.style.StyleSpan;
import android.util.LruCache;

import org.jetbrains.annotations.NotNull;

import kh.com.mysabay.sdk.R;

/**
 * Created by Tan Phirum on 3/6/18.
 */
public class FontUtils {

    @NotNull
    public static String toKhmerText(Context context, String text) {
        return getEmbeddedString(context, text, R.font.font_battambang_regular, false).toString();
    }

    @NotNull
    public static String toKhmerTextBold(Context context, String text) {
        return getEmbeddedString(context, text, R.font.font_battambang_bold, true).toString();
    }

    @NotNull
    public static SpannableString toKhmerSpanText(Context context, String text) {
        return toKhmerSpanTextBold(context, text, false);
    }

    @NotNull
    public static SpannableString toKhmerSpanTextBold(Context context, String text, boolean isBold) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new TypefaceSpan(context, R.font.font_battambang_regular), 0,
                spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (isBold)
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        // Update the action bar name with the TypefaceSpan instance
        return spanString;
    }

    /**
     * Convert text with font and return back as string
     *
     * @param context
     * @param title
     * @param fontIdPath
     * @param bold
     * @return string after converted
     */
    @NotNull
    public static SpannableString getEmbeddedString(Context context, String title, int fontIdPath, boolean bold) {
        SpannableString spanString = new SpannableString(title);
        spanString.setSpan(new TypefaceSpan(context, fontIdPath), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (bold)
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        // Update the action bar name with the TypefaceSpan instance
        return spanString;
    }

    public static class TypefaceSpan extends MetricAffectingSpan {
        /**
         * An <code>LruCache</code> for previously loaded typefaces.
         */
        private static LruCache<Integer, Typeface> sTypefaceCache =
                new LruCache<>(12);

        private Typeface mTypeface;

        /**
         * Load the {@link Typeface} and apply to a {@link Spannable}.
         */
        public TypefaceSpan(Context context, int fontIdPath) {
            mTypeface = sTypefaceCache.get(fontIdPath);

            if (mTypeface == null) {
                mTypeface = ResourcesCompat.getFont(context, fontIdPath);
                // Cache the loaded Typeface
                sTypefaceCache.put(fontIdPath, Typeface.create(mTypeface, Typeface.NORMAL));
            }
        }

        @Override
        public void updateMeasureState(@NotNull TextPaint p) {
            p.setTypeface(Typeface.create(mTypeface, Typeface.NORMAL));

            // Note: This flag is required for proper typeface rendering
            p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }

        @Override
        public void updateDrawState(@NotNull TextPaint tp) {
            tp.setTypeface(Typeface.create(mTypeface, Typeface.NORMAL));

            // Note: This flag is required for proper typeface rendering
            tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }

    public static Typeface getTypefaceKhmer(Context context) {
        return ResourcesCompat.getFont(context, R.font.font_battambang_regular);
    }

    public static Typeface getTypefaceKhmerBold(Context context) {
        return ResourcesCompat.getFont(context, R.font.font_battambang_bold);
    }
}
