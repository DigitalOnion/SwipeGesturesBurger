package com.outerspace.swipegesturesburger;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SwipeOnFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_on_fragment);

        FragmentManager manager = getSupportFragmentManager();
        SwipeButtonsFragment fragment = new SwipeButtonsFragment();
        manager.beginTransaction().add(R.id.fragment_layout, fragment).commit();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        fragment.setDisplayMetrics(displayMetrics);
    }
}
