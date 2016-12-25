package com.karlchu.medicalorder.ui.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.karlchu.medicalorder.core.db.Repo;


/**
 * Created by hieu on 4/11/2016.
 */
public class BaseFragment extends Fragment {
    protected MainActivity parent;
    protected Repo repo;
    protected Handler handler = new Handler();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (MainActivity)getActivity();
        repo = new Repo(parent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    public MainActivity getParentActivity() {
        return parent;
    }



}
