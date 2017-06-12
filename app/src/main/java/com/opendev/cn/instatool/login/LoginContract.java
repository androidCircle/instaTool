package com.opendev.cn.instatool.login;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instatool.BasePresenter;

import io.reactivex.Observable;

/**
 * Created by root on 02/06/17.
 */

public interface LoginContract {

    interface View {

        void enableButton(boolean enable);

        void makeToast(String message);

        void makeToast(@StringRes int messageId);

        String getUsername();

        String getPassword();

        void setPresenter(LoginContract.Presenter presenter);

        void showProgressSpinner(boolean showSpinner);

        void setUserInputError();

        void setPassInputError();

        void loginFinished(Instagram4Android instagram);

    }

    interface Presenter extends BasePresenter {

        void observeEditableFieldChanges(Observable<ViewValuesModel> observable);

        void onLoginClick();

    }

}
