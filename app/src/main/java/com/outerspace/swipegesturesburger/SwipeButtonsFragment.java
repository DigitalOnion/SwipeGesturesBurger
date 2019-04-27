package com.outerspace.swipegesturesburger;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

public class SwipeButtonsFragment extends Fragment {

    GestureDetectorCompat detector;
    ImageView vanImage;
    ImageView cupImage;
    DisplayMetrics displayMetrics;

    public SwipeButtonsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_swipe_objects, container, false);

        detector = new GestureDetectorCompat(getContext(), new MyGestureListener());

        return inflatedView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vanImage = getActivity().findViewById(R.id.icon_van);
        cupImage = getActivity().findViewById(R.id.icon_coffee_cup);

        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        };

//        View.OnTouchListener listener = (touchView, event) -> {
//            detector.onTouchEvent(event);
//            return true;
//        };
        vanImage.setOnTouchListener(listener);
        cupImage.setOnTouchListener(listener);
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    private void swapIcons() {

        MyAnimatorUpdateListener vanListener = new MyAnimatorUpdateListener(vanImage);
        MyAnimatorUpdateListener cupListener = new MyAnimatorUpdateListener(cupImage);

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
            float swipeLength = SwipeUtils.swipeLength(e1, e2);

            SwipeUtils.SwipeType swipe = SwipeUtils.swipeType(e1, e2);
            float percent = SwipeUtils.swipePercent(displayMetrics, e1, e2);
            if(percent >= 0.2f &&
                    ( swipe == SwipeUtils.SwipeType.SWIPE_RIGHT || swipe == SwipeUtils.SwipeType.SWIPE_LEFT)) {
                swapIcons();
            }
            return true;
        }
    }
}
