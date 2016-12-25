package com.karlchu.medicalorder.core.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Linh Nguyen on 11/21/2016.
 */

public class SnackbarUtils {
    public static Snackbar buildSnackbar(View view, String errorTitle, String buttonTitle, final Runnable onTryCallback) {
        Snackbar snackbar = Snackbar
                .make(view, errorTitle, Snackbar.LENGTH_INDEFINITE)
                .setAction(buttonTitle, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onTryCallback != null) {
                            onTryCallback.run();
                        }
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        return snackbar;
    }
}
