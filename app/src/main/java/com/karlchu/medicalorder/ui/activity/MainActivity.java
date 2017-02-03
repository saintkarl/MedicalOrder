package com.karlchu.medicalorder.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.karlchu.medicalorder.R;

public class MainActivity extends BaseDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new HomeFragment()).addToBackStack(null).commitAllowingStateLoss();
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
