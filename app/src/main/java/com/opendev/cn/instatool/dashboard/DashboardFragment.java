package com.opendev.cn.instatool.dashboard;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opendev.cn.instatool.GlobalInstagram;
import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.data.UserProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by root on 09/06/17.
 */

public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter presenter;
    private CardViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Unbinder unbinder;

    @BindView(R.id.self_username)
    TextView selfUsername;
    @BindView(R.id.profile_pic)
    ImageView profilePic;
    @BindView(R.id.self_followers)
    TextView selfFollowers;
    @BindView(R.id.self_following)
    TextView selfFollowing;
    @BindView(R.id.self_unfollowers)
    TextView selfUnfollowers;
    @BindView(R.id.tab_text)
    TextView tabText;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(presenter == null) {
            presenter = new DashboardPresenter(this);
        }

        presenter.setIg(GlobalInstagram.getInstagram4Android());
        presenter.subscribe();
        presenter.initializeViews();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), getActivity(), 2);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    tabText.setText(getActivity().getString(R.string.followers_list));
                } else {
                    tabText.setText(getActivity().getString(R.string.unfollowers_list));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeToast(int messageId) {
        Toast.makeText(getActivity(), messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setProfilePic(String uri) {
        Picasso.with(getActivity()).load(uri).fit().into(profilePic);
    }

    @Override
    public void setSelfFollowers(int followers) {
        selfFollowers.setText(selfFollowers.getText() + "  " + followers);
    }

    @Override
    public void setSelfFollowing(int following) {
        selfFollowing.setText(selfFollowing.getText() + "  " + following);
    }

    @Override
    public void setSelfUsername(String username) {
        selfUsername.setText(username);
        ObjectAnimator animator = new ObjectAnimator().ofFloat(selfUsername, "alpha", 0, 1);
        animator.setDuration(800);
        animator.start();
    }

    @Override
    public void setSelfUnfollowers(int unfollowersNum) {
        selfUnfollowers.setText(selfUnfollowers.getText() + "  " + unfollowersNum);
    }


    class PagerAdapter extends FragmentPagerAdapter {

        int numOfTabs = 0;
        Context context;

        public PagerAdapter(FragmentManager fm, Context context, int numOfTabs) {
            super(fm);
            this.context = context;
            this.numOfTabs = numOfTabs;
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FollowersListFragment();
                case 1:
                    return new UnfollowersListFragment();
            }

            return null;
        }
    }

}
