package com.opendev.cn.instatool.login;

/**
 * Created by root on 04/06/17.
 */

public class ViewValuesModel {

    private CharSequence username;
    private CharSequence password;

    public ViewValuesModel(CharSequence username, CharSequence password) {
        this.username = username;
        this.password = password;
    }

    public CharSequence getUsername() {
        return username;
    }

    public CharSequence getPassword() { return password; }
}
