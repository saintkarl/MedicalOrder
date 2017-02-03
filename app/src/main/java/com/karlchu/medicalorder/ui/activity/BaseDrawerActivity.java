package com.karlchu.medicalorder.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.utils.ELog;
import com.karlchu.medicalorder.ui.adapter.AppConstant;
import com.karlchu.medicalorder.ui.utils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hieu Le on 4/11/2016.
 */

public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Nullable @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @Nullable @BindView(R.id.nav_view)
    NavigationView navigationView;


    private boolean isNeedReSetupActionBar;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
}

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
        setupToolbar();

        navigationView.setNavigationItemSelectedListener(this);
        bindEvents();
        hideNavigationItem();


    }

    private void bindEvents() {
        View header = navigationView.getHeaderView(0);
        ImageView circleImageView = (ImageView) header.findViewById(R.id.user_profile_image);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (!(fragment instanceof HomeFragment)) {
                    replaceFragmentContent(new HomeFragment());
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }


    @Override
    public void onBackPressed() {
        // Auto hide keyboard if exist
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                finish();
            } else {
                if (isNeedReSetupActionBar) {
                    setupToolbar();
                    isNeedReSetupActionBar = false;
                }
                super.onBackPressed();
            }
        }

    }

    protected void bindViews() {
        ButterKnife.bind(this);

    }

    private void hideNavigationItem() {
        String role = UserUtils.getUserRole();

        // Home action menu with authorities
        if (role.equals(AppConstant.AUTH_SM) || role.equals(AppConstant.AUTH_GENERAL)) {
            setDrawerState(false);
        }


//        Menu navMenu = navigationView.getMenu();
//
//        navMenu.findItem(R.id.nav_history_personal).setVisible(false);
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().show();

            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            toggle.syncState();
            drawer.setDrawerListener(toggle);

            //Show icon back and hamburger when switch fragment
            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    } else {
                        //show hamburger
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        toggle.syncState();
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawer.openDrawer(GravityCompat.START);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_home) {
            String role = UserUtils.getUserRole();

            // Home action menu with authorities
            if (role.equals(AppConstant.AUTH_CUSTOMER)) { // Customer role
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
//                if (!(fragment instanceof DashBoardFragment)) {
//                    if(fragment instanceof PersonalInfoFragment) {
//                        reSetupToolbar();
//                    }
//                    replaceFragmentContent(new DashBoardFragment());
//                }
            } else if (role.equals(AppConstant.AUTH_SM)) { // Sm Role
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
//                if (!(fragment instanceof HomeFragment)) {
//                    if(fragment instanceof PersonalInfoFragment) {
//                        reSetupToolbar();
//                    }
//                    replaceFragmentContent(new SalesHomeFragment());
//                }
            } else if (role.equals(AppConstant.AUTH_GENERAL)) { // Other role
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
//                if (!(fragment instanceof HomeFragment)) {
//                    if(fragment instanceof PersonalInfoFragment) {
//                        reSetupToolbar();
//                    }
//                }
            }

        } else if (id == R.id.action_change_password) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (!(fragment instanceof ChangePasswordFragment)) {
                replaceFragmentContent(new ChangePasswordFragment());
            }
        } else if(id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseDrawerActivity.this);
            builder.setTitle(getString(R.string.log_out));
            builder.setMessage(getString(R.string.sure_log_out));
            builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserUtils.logOut(BaseDrawerActivity.this);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        clearFragmentBackStack();
        if (isNeedReSetupActionBar) {
            setupToolbar();
            isNeedReSetupActionBar = false;
        }

        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_history_personal) {
//            replaceFragmentContent(new PersonalHistoryFragment());
//        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    protected void replaceFragmentContent(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void replaceFragmentContentNoBack(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }



    protected void replaceFragmentContent(Class fragment) {
        try {
            replaceFragmentContent((Fragment) fragment.newInstance());
        } catch (Exception e) {
            ELog.e(e.getMessage(), e);
        }
    }

    protected void clearFragmentBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            for (int i = 1; i < fragmentManager.getBackStackEntryCount(); i++) {
                //remove other fragments from stack
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    public boolean isNeedReSetupActionBar() {
        return isNeedReSetupActionBar;
    }

    public void setNeedReSetupActionBar(boolean needReSetupActionBar) {
        isNeedReSetupActionBar = needReSetupActionBar;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return toggle;
    }

    public void retainStateFragment(Bundle savedInstanceState, String tag) {
        if(savedInstanceState != null) {
            replaceFragmentContent(getSupportFragmentManager().getFragment(savedInstanceState, tag));
        }
    }

    public void reSetupToolbar() {
        setNeedReSetupActionBar(true);
        setupToolbar();
        isNeedReSetupActionBar = false;
    }

    public void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        }
        else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }


}
