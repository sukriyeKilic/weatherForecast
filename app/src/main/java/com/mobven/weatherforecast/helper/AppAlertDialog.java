package com.mobven.weatherforecast.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.Html;

import com.mobven.weatherforecast.R;


public class AppAlertDialog {

    public static void showMessage(Activity context, String msg) {
        showMessage(context, null, msg, true, null, null, null, null);
    }

    public static void showMessage(Activity context, String title, String msg, boolean cancelable, String positiveBtn,
                                   Handler positiveBtnHandler) {
        showMessage(context, title, msg, cancelable, positiveBtn, null, positiveBtnHandler, null);
    }

    public static void showMessage(Activity context, String title, String msg, boolean cancelable, String positiveBtn,
                                   String negativeBtn, final Handler positiveBtnHandler, final Handler negativeBtnHandler) {

        showMessage(context, title, msg, cancelable, positiveBtn, negativeBtn, positiveBtnHandler, negativeBtnHandler, null, null);
    }

    public static void showMessage(Activity context, String title, String msg, boolean cancelable, String positiveBtn,
                                   String negativeBtn, final Handler positiveBtnHandler, final Handler negativeBtnHandler,
                                   String neutralBtn, final Handler neutralBtnHandler) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(cancelable);

        if (title == null) {
            builder.setTitle(context.getResources().getString(R.string.app_name));
        } else {
            builder.setTitle(title);
        }

        if (msg != null)
            builder.setMessage(Html.fromHtml(msg));

        if (positiveBtn == null)
            positiveBtn = "Tamam";

        builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (positiveBtnHandler != null) {
                    positiveBtnHandler.sendEmptyMessage(0);
                }
            }
        });

        if (negativeBtn != null) {
            builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (negativeBtnHandler != null) {
                        negativeBtnHandler.sendEmptyMessage(0);
                    }
                }
            });
        }

        if (neutralBtn != null) {
            builder.setNeutralButton(neutralBtn, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (neutralBtnHandler != null) {
                        neutralBtnHandler.sendEmptyMessage(0);
                    }
                }
            });
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builder.create().show();
            }
        });

    }
}

