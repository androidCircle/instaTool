package com.opendev.cn.instatool.dashboard;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instagram4android.requests.InstagramGetUserFollowersRequest;
import com.opendev.cn.instagram4android.requests.InstagramGetUserFollowingRequest;
import com.opendev.cn.instagram4android.requests.InstagramSearchUsernameRequest;
import com.opendev.cn.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import com.opendev.cn.instagram4android.requests.payload.InstagramSearchUsernameResult;
import com.opendev.cn.instagram4android.requests.payload.InstagramUser;
import com.opendev.cn.instagram4android.requests.payload.InstagramUserSummary;
import com.opendev.cn.instatool.data.UserProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 09/06/17.
 */

public class DashboardPresenter implements DashboardContract.Presenter {

    private DashboardContract.View view;
    private Instagram4Android instagram;

    @Override
    public void setIg(Instagram4Android instagram) {
        this.instagram = instagram;
    }

    public DashboardPresenter(DashboardContract.View view) {
        this.view = view;
    }

    // Subscribes to pre initialised Observables and updates when the data set is changed
    @Override
    public void subscribe() {
        getSelfUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(selfUser -> {
            view.setProfilePic(selfUser.getProfile_pic_url());
            view.setSelfFollowers(selfUser.getFollower_count());
            view.setSelfFollowing(selfUser.getFollowing_count());
            view.setSelfUsername(selfUser.getUsername());
        });
        getUnfollowers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unfollowers -> view.setSelfUnfollowers(unfollowers.size()));
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initializeViews() {

    }

    private Observable<InstagramUser> getSelfUser() {

        Observable<InstagramUser> observable = Observable.create(observableEmitter -> {

            InstagramSearchUsernameResult result = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));
            observableEmitter.onNext(result.getUser());

        });

        return observable;

    }

    private Observable<List<InstagramUserSummary>> getUnfollowers() {

        Observable<List<InstagramUserSummary>> observable = Observable.create(observableEmitter -> {

            InstagramSearchUsernameResult usernameResult = null;
            List<InstagramUserSummary> followers = null;
            List<InstagramUserSummary> following = null;
            List<InstagramUserSummary> unFollowers = new ArrayList<>();

            try {
                usernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));
                followers = instagram.sendRequest(new InstagramGetUserFollowersRequest(usernameResult.getUser().getPk())).getUsers();
                following = instagram.sendRequest(new InstagramGetUserFollowingRequest(usernameResult.getUser().getPk())).getUsers();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(InstagramUserSummary person : following) {
                if(!(followers.contains(person))) {
                    unFollowers.add(person);
                }
            }

            observableEmitter.onNext(unFollowers);

        });

        return observable;

    }

    private void getBio(String username) {

    }

}
