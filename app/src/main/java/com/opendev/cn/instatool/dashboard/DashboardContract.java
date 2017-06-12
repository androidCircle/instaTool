package com.opendev.cn.instatool.dashboard;

import android.support.annotation.StringRes;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instatool.BasePresenter;
import com.opendev.cn.instatool.data.UserProfile;

import java.util.List;

/**
 * Created by root on 09/06/17.
 */

public interface DashboardContract {

    interface View {

        void makeToast(String message);

        void makeToast(@StringRes int messageId);

        void setPresenter(DashboardContract.Presenter presenter);

        void setProfilePic(String uri);

        void setSelfFollowers(int followers);

        void setSelfFollowing(int following);

        void setSelfUsername(String username);

        void setSelfUnfollowers(int unfollowersNum);

    }

    interface Presenter extends BasePresenter {

        void initializeViews();

        void setIg(Instagram4Android instagram);

    }

}
