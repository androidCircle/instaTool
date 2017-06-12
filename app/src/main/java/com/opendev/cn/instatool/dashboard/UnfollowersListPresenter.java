package com.opendev.cn.instatool.dashboard;

import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instagram4android.requests.InstagramGetUserFollowersRequest;
import com.opendev.cn.instagram4android.requests.InstagramGetUserFollowingRequest;
import com.opendev.cn.instagram4android.requests.InstagramSearchUsernameRequest;
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
 * Created by root on 11/06/17.
 */

public class UnfollowersListPresenter implements FollowersListContract.Presenter {

    private FollowersListContract.View view;
    private Instagram4Android instagram;
    private List<UserProfile> userProfiles;

    @Override
    public void setIg(Instagram4Android instagram) {
        this.instagram = instagram;
    }

    public UnfollowersListPresenter(FollowersListContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        getUserUnfollowers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(profiles -> {
            userProfiles = profiles;
            view.updateCardView(userProfiles);
        });
    }

    private Observable<List<UserProfile>> getUserUnfollowers() {

        Observable<List<UserProfile>> observable = Observable.create(observableEmitter -> {

            List<UserProfile> unfollowerProfiles = new ArrayList<>();

            InstagramSearchUsernameResult usernameResult = null;
            List<InstagramUserSummary> followers = null;
            List<InstagramUserSummary> following = null;

            try {
                usernameResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));
                followers = instagram.sendRequest(new InstagramGetUserFollowersRequest(usernameResult.getUser().getPk())).getUsers();
                following = instagram.sendRequest(new InstagramGetUserFollowingRequest(usernameResult.getUser().getPk())).getUsers();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(InstagramUserSummary person : following) {
                if(!(followers.contains(person))) {
                    unfollowerProfiles.add(new UserProfile(person.getUsername(), person.getFull_name(), person.getProfile_pic_url(), person.is_verified()));
                }
            }

            observableEmitter.onNext(unfollowerProfiles);

        });

        return observable;

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initializeViews() {

    }
}
