package com.opendev.cn.instatool.dashboard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.data.UserProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 09/06/17.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private int count = 0;
    private Context context;
    private List<UserProfile> users;

    public CardViewAdapter(Context context, List<UserProfile> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserProfile profile = users.get(position);

        holder.userName.setText(profile.getUserName());
        if(profile.getUserRealName().length() > 40) {
            holder.userRealName.setText(profile.getUserRealName().substring(0, 37) + "...");
        } else {
            holder.userRealName.setText(profile.getUserRealName());
        }
        Picasso.with(context).load(profile.getProfilePicUrl()).fit().into(holder.image);
        if(profile.isVerified()) {
            holder.userVerified.setText(R.string.is_verified);
            holder.userVerified.setTextColor(Color.BLUE);
        } else {
            holder.userVerified.setText(R.string.is_not_verified);
            holder.userVerified.setTextColor(Color.parseColor("#808080"));
        }

        if(count < getItemCount()) {
            setFadeAnimation(holder.itemView);
            count++;
        }

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        holder.clearAnimation();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.user_real_name)
        TextView userRealName;
        @BindView(R.id.follow_status)
        TextView followStatus;
        @BindView(R.id.user_verified)
        TextView userVerified;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }
}
