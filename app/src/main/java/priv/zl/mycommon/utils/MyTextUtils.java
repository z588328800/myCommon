package priv.zl.mycommon.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;

public class MyTextUtils {

    /**
     * 设置可点击字符串
     *
     * @param str           总字符串
     * @param clickStr      可点击字符串
     * @param clickableSpan 点击事件
     */
    public static SpannableString getSpan(String str, String clickStr, ClickableSpan clickableSpan) {

        //可以重写ClickableSpan的方法用于设置字体颜色和事件
//        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
//            //设置文本的颜色
//            ds.setColor(Color.RED);
//            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
//            ds.setUnderlineText(false);
//        }
//
//        public void onClick(View widget) {
//            Toast.makeText(context,"发生了点击效果",Toast.LENGTH_SHORT).show();
//        }

        SpannableString SpanStr = new SpannableString(str);
        int firstChar = str.indexOf(clickStr);
        SpanStr.setSpan(clickableSpan, firstChar, firstChar + clickStr.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return SpanStr;
    }

}
