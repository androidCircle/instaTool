package com.opendev.cn.instatool.data;

/**
 * Created by root on 09/06/17.
 */

public class UserProfile {

    private String userName;
    private String userRealName;
    private String profilePicUrl;
    private boolean isVerified;

    public UserProfile(String userName, String userRealName, String profilePicUrl, boolean isVerified) {
        this.userName = userName;
        this.userRealName = userRealName;
        this.profilePicUrl = profilePicUrl;
        this.isVerified = isVerified;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRealName() { return userRealName; }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public boolean isVerified() { return isVerified; }
}
