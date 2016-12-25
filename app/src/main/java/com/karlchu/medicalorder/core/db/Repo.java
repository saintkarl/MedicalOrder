package com.karlchu.medicalorder.core.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by hieu on 8/03/2016.
 */
public class Repo {
    private DatabaseHelper databaseHelper;

    public Repo(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void release() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }
    }
}
