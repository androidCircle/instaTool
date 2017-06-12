package com.opendev.cn.instatool.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.opendev.cn.instatool.GlobalInstagram;
import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.data.UserProfile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by root on 11/06/17.
 */

public class FollowersListFragment extends Fragment implements FollowersListContract.View {

    private FollowersListContract.Presenter presenter;
    private CardViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public FollowersListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(presenter == null) {
            presenter = new FollowersListPresenter(this);
        }

        presenter.setIg(GlobalInstagram.getInstagram4Android());
        presenter.subscribe();
        presenter.initializeViews();

        View view = inflater.inflate(R.layout.fragment_followers_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        return view;

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeToast(int messageId) {
        Toast.makeText(getActivity(), getString(messageId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(FollowersListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateCardView(List<UserProfile> userProfiles) {

        adapter = new CardViewAdapter(getActivity(), userProfiles);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }



}
