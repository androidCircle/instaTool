package com.opendev.cn.instatool.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by root on 03/06/17.
 */

public class ActivityUtils extends AppCompatActivity {

    public static void addFragmentToActivity(FragmentManager manager,
                                             Fragment fragment,
                                             int frameLayoutId,
                                             String tag) {

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(frameLayoutId, fragment, tag);
        fragmentTransaction.commit();

    }

}
