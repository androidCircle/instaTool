package com.opendev.cn.instatool.login;

import android.content.res.Resources;
import android.util.Log;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instatool.R;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.text.TextUtils.isEmpty;

/**
 * Created by root on 02/06/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private Disposable disposable;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void onLoginClick() {

        String username = view.getUsername();
        String password = view.getPassword();

        loginToInstagram(username, password);

    }

    private void loginToInstagram(final String username, final String password) {

        Instagram4Android instagram = Instagram4Android.builder().username(username).password(password).build();
        Completable.fromAction(() -> instagram.setup())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() ->
                        Completable.fromAction(() -> instagram.login())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> view.loginFinished(instagram)));

    }

    private Request buildRequest(final String url) {
        return new Request.Builder().url(url).build();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void observeEditableFieldChanges(Observable<ViewValuesModel> observable) {

         disposable = observable.map(viewStates -> {

            boolean userValid = !isEmpty(viewStates.getUsername());
            boolean passValid = !isEmpty(viewStates.getPassword());

            if(!userValid) {
                view.setUserInputError();
            }

            if(!passValid) {
                view.setPassInputError();
            }

            return userValid && passValid;

        })
        .subscribe(enable -> view.enableButton(enable));

    }

    @Override
    public void unSubscribe() {
        // We don't need to subscribe after new activity started
        disposable.dispose();
    }
}
