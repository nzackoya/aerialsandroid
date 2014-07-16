package com.aerials.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import com.aerials.app.R;

public class MessageToUser {

    public static void showMessage(int textId, Context context) {
        TextView content = new TextView(context);
        content.setText(textId);
        constructDialog(content, context, null);
    }

    public static void showMessage(String text, Context context) {
        TextView content = new TextView(context);
        content.setText(text);
        constructDialog(content, context, null);
    }

    public static void showMessage(int textId, Context context, DialogInterface.OnClickListener onClickListener) {
        TextView content = new TextView(context);
        content.setText(textId);
        constructDialog(content, context, onClickListener);
    }

    public static void showMessage(String text, Context context, DialogInterface.OnClickListener onClickListener) {
        TextView content = new TextView(context);
        content.setText(text);
        constructDialog(content, context, onClickListener);
    }

    public static void showDefaultErrorMessage(Context context) {
        showMessage(R.string.error_try_again, context);
    }

    private static void constructDialog(TextView content, Context context, DialogInterface.OnClickListener onClickListener) {
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        content.setPadding(0, 20, 0, 20);
        content.setGravity(Gravity.CENTER);
        AlertDialog.Builder attention = new AlertDialog.Builder(context);
        attention.setTitle(R.string.attention_label);
        attention.setView(content);
        attention.setNeutralButton(R.string.close_label, onClickListener);
        attention.show();
    }
}
