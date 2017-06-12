package com.opendev.cn.instatool;

import com.opendev.cn.instagram4android.Instagram4Android;

/**
 * Created by root on 09/06/17.
 */

public class GlobalInstagram {

    private static Instagram4Android instagram4A;

    public static void setInstagram(Instagram4Android instagram) { instagram4A = instagram; }

    public static Instagram4Android getInstagram4Android() {
        return instagram4A;
    }

}
