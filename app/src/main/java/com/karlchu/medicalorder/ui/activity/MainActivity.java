package com.karlchu.medicalorder.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.A;
import com.karlchu.medicalorder.ui.adapter.AppConstant;
import com.karlchu.medicalorder.ui.utils.UserUtils;

public class MainActivity extends BaseDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String role = UserUtils.getUserRole();
//        replaceFragmentContent(DashBoardFragment.class);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Hide home menu when require change password
        MenuItem homeMenu = menu.findItem(R.id.action_home);

        if (!homeMenu.isVisible()) {
            homeMenu.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);

    }
}
