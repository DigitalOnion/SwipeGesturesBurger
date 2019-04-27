package com.outerspace.swipegesturesburger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat detector;
    private TextView textMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        detector = new GestureDetectorCompat(this, new MainActivity.MyGestureListener());
        textMonitor = findViewById(R.id.text_monitor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_swipe_on_activity:
                Intent intentOnActivity = new Intent(this, SwipeButtonsActivity.class);
                startActivity(intentOnActivity);
                break;

            case R.id.action_swipe_on_fragment:
                Intent intentOnFragment = new Intent(this, SwipeOnFragmentActivity.class);
                startActivity(intentOnFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            StringBuilder sb = new StringBuilder();
            String spacer = "    ";
            MotionEvent[] events = {e1, e2};
            String[] titles = getResources().getStringArray(R.array.motion_event_titles);
            for(int i = 0; i < 2; i++) {
                sb.append(titles[i]).append('\n');
                sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.action), SwipeUtils.actionMaskedName(events[i]))).append('\n');
                sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.coordinates), events[i].getX(), events[i].getY())).append('\n');
            }
            sb.append(getString(R.string.velocity)).append('\n');
            sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.velocity_val), "X", velocityX)).append('\n');
            sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.velocity_val), "Y", velocityY)).append('\n');
            sb.append(getString(R.string.angle)).append('\n');
            sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.angle_val), Math.toDegrees(SwipeUtils.angleRadians(e1, e2)))).append('\n');
            sb.append(getString(R.string.length)).append('\n');
            sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.length_val), SwipeUtils.swipeLength(e1, e2))).append('\n');
            sb.append(getString(R.string.orientation)).append('\n');
            sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.orientation_val), "e1", e1.getOrientation(e1.getPointerId(0)))).append('\n');
            sb.append(spacer).append(String.format(Locale.getDefault(), getString(R.string.orientation_val), "e2", e2.getOrientation(e2.getPointerId(0)))).append('\n');
            sb.append(SwipeUtils.swipeType(e1, e2).toString());
            textMonitor.setText(sb.toString());
            return true;
        }
    }

}
