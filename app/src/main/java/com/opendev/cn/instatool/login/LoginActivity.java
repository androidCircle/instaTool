package com.opendev.cn.instatool.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.utils.ActivityUtils;

/**
 * Created by root on 02/06/17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_FRAGMENT = "LOGIN_FRAGMENT";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        fragmentManager = this.getSupportFragmentManager();

        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentByTag(LOGIN_FRAGMENT);
        if(loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(fragmentManager, loginFragment, R.id.login_fragment, LOGIN_FRAGMENT);

    }

}
