package com.outerspace.swipegesturesburger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SwipeButtonsOnFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_buttons_on_fragment);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = new ButtonFragment();
        manager.beginTransaction().add(R.id.fragment_layout, fragment).commit();
    }



}
