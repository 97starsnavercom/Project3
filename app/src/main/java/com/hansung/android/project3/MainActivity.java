package com.hansung.android.project3;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mCountDown;
    ImageView mfire;
    final String TAG = "Project3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCountDown = (ImageView) findViewById(R.id.countdown);
        mfire = (ImageView) findViewById(R.id.fire);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCountDownFrameAnimation();
        startFireTweenAnimation();
    }

    private void startCountDownFrameAnimation() {
        mCountDown.setBackgroundResource(R.drawable.frame_anim);
        AnimationDrawable countdownAnim = (AnimationDrawable) mCountDown.getBackground();

        countdownAnim.start();


    }

    private void startFireTweenAnimation() {
        Animation fire_anim = AnimationUtils.loadAnimation(this, R.anim.fire);
        mfire.startAnimation(fire_anim);
        fire_anim.setAnimationListener(animationListener);
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.i(TAG, "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.i(TAG, "onAnimationEnd");
            finish();
            startActivity(new Intent(getApplicationContext(), Restaurant_Map.class));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.i(TAG, "onAnimationRepeat");
        }
    };
}
