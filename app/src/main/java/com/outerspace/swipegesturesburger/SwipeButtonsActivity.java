package com.outerspace.swipegesturesburger;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;


public class SwipeButtonsActivity extends AppCompatActivity {

    private GestureDetectorCompat detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_buttons);

        detector = new GestureDetectorCompat(this, new MyGestureListener());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void swapIcons() {
        ImageView iconVan = findViewById(R.id.icon_van);
        ImageView iconCup = findViewById(R.id.icon_coffee_cup);

        MyAnimatorUpdateListener vanListener = new MyAnimatorUpdateListener(iconVan);
        MyAnimatorUpdateListener cupListener = new MyAnimatorUpdateListener(iconCup);

        ValueAnimator vanAnimator = ValueAnimator.ofFloat(vanListener.getHorizontalBias(), cupListener.getHorizontalBias());
        vanAnimator.addUpdateListener(vanListener);
        vanAnimator.setDuration(500);
        ValueAnimator cupAnimator = ValueAnimator.ofFloat(cupListener.getHorizontalBias(), vanListener.getHorizontalBias());
        cupAnimator.addUpdateListener(cupListener);
        cupAnimator.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.play(vanAnimator).with(cupAnimator);
        set.start();
    }

    private class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        ImageView image;
        ConstraintLayout.LayoutParams params;

        MyAnimatorUpdateListener(ImageView image) {
            this.image = image;
            params = (ConstraintLayout.LayoutParams) image.getLayoutParams();
        }

        public float getHorizontalBias() {
            return params.horizontalBias;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator updatedAnimation) {
            params.horizontalBias = (float) updatedAnimation.getAnimatedValue();
            image.setLayoutParams(params);
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            SwipeUtils.SwipeType swipe = SwipeUtils.swipeType(e1, e2);
            // float percent = SwipeUtils.swipePercent(e1, e2);
            if(( swipe == SwipeUtils.SwipeType.SWIPE_RIGHT || swipe == SwipeUtils.SwipeType.SWIPE_LEFT)) {
                swapIcons();
            }
            return true;
        }
    }
}
