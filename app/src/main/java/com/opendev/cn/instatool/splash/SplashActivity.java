package com.opendev.cn.instatool.splash;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 02/06/17.
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_title)
    TextView splashTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
        animate();

    }

    private void animate() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(splashTitle, "alpha", 0, 1);
        animator.setDuration(500);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

}
