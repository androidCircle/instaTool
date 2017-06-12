package com.opendev.cn.instatool.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.login.LoginFragment;
import com.opendev.cn.instatool.utils.ActivityUtils;

/**
 * Created by root on 09/06/17.
 */

public class DashboardActivity extends AppCompatActivity {

    private static final String DASHBOARD_FRAGMENT = "DASHBOARD_FRAGMENT";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        fragmentManager = this.getSupportFragmentManager();

        DashboardFragment dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag(DASHBOARD_FRAGMENT);
        if(dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(fragmentManager, dashboardFragment, R.id.dashboard_fragment, DASHBOARD_FRAGMENT);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
