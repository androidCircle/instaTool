package com.opendev.cn.instatool.dashboard;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by root on 10/06/17.
 */

public class FirstTimeSlideInBottomAnimationAdapter extends SlideInBottomAnimationAdapter {

    private int mDuration = 700;
    private Interpolator mInterpolator = new LinearInterpolator();

    public void setDuration(int mDuration) {
        super.setDuration(mDuration);
        this.mDuration = mDuration;
    }

    public void setInterpolator(Interpolator mInterpolator) {
        super.setInterpolator(mInterpolator);
        this.mInterpolator = mInterpolator;
    }

    public FirstTimeSlideInBottomAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        viewHolder.itemView.animate()
                .setDuration(1)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Animator[] animators = getAnimators(viewHolder.itemView);
                        for (Animator animator : animators) {
                            animator.setDuration(mDuration).start();
                            animation.setInterpolator(mInterpolator);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
// other empty override function
                }).start();
        return viewHolder;
    }

}
