package com.karlchu.medicalorder.core.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by hieu on 7/4/2016.
 */
public abstract class EAsyncTask extends AsyncTask {
    private ProgressDialog progressDialog;

    public EAsyncTask(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public EAsyncTask() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
