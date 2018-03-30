package com.example.androidlast_middle_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimActivity extends AppCompatActivity {

    private ImageView anim_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        initView();
        initData();
    }

    private void initData() {
        Animation animation = AnimationUtils.loadAnimation(AnimActivity.this, R.anim.img);
        anim_img.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                startActivity(new Intent(AnimActivity.this,MainActivity.class));
            }
        });
    }

    private void initView() {
        anim_img = (ImageView) findViewById(R.id.anim_img);
    }
}
