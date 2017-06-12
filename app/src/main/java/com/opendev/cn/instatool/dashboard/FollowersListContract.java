package com.opendev.cn.instatool.dashboard;

import android.support.annotation.StringRes;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instatool.BasePresenter;
import com.opendev.cn.instatool.data.UserProfile;

import java.util.List;

/**
 * Created by root on 11/06/17.
 */

public interface FollowersListContract {

    interface View {

        void makeToast(String message);

        void makeToast(@StringRes int messageId);

        void setPresenter(FollowersListContract.Presenter presenter);

        void updateCardView(List<UserProfile> profiles);

    }

    interface Presenter extends BasePresenter {

        void initializeViews();

        void setIg(Instagram4Android instagram);

    }

}
