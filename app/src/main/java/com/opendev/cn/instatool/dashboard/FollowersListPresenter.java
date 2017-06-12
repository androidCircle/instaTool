package com.opendev.cn.instatool.dashboard;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instagram4android.requests.InstagramGetUserFollowersRequest;
import com.opendev.cn.instagram4android.requests.InstagramSearchUsernameRequest;
import com.opendev.cn.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import com.opendev.cn.instagram4android.requests.payload.InstagramSearchUsernameResult;
import com.opendev.cn.instagram4android.requests.payload.InstagramUserSummary;
import com.opendev.cn.instatool.data.UserProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 11/06/17.
 */

public class FollowersListPresenter implements FollowersListContract.Presenter {

    private FollowersListContract.View view;
    private Instagram4Android instagram;
    private List<UserProfile> userProfiles;

    @Override
    public void setIg(Instagram4Android instagram) {
        this.instagram = instagram;
    }

    public FollowersListPresenter(FollowersListContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        getUserProfile().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(profiles -> {
            userProfiles = profiles;
            view.updateCardView(userProfiles);
        });
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initializeViews() {

    }

    private Observable<List<UserProfile>> getUserProfile() {

        Observable<List<UserProfile>> observable = Observable.create(observableEmitter -> {

            InstagramSearchUsernameResult usernameResult = null;
            List<InstagramUserSummary> followers = null;
            List<UserProfile> follows = new ArrayList<>();

            try{
                usernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));
                InstagramGetUserFollowersResult followersResult = instagram.sendRequest(new InstagramGetUserFollowersRequest(usernameResult.getUser().getPk()));
                followers = followersResult.getUsers();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(followers != null && followers.size() > 0) {
                for(InstagramUserSummary user: followers) {
                    follows.add(new UserProfile(user.getUsername(), user.getFull_name(), user.getProfile_pic_url(), user.is_verified()));
                }
                observableEmitter.onNext(follows);
            }

        });

        return observable;

    }

}
