package com.karlchu.medicalorder.core.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by hieu on 6/30/2016.
 */
public class DialogBuilder {
    public static Dialog buildConfirmDlg(Context context, String title, String msg, final Runnable onPositiveCallback, final Runnable onNegativeCallback, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onPositiveCallback != null) {
                    onPositiveCallback.run();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onNegativeCallback != null) {
                    onNegativeCallback.run();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        return dialog;
    }
}
