package com.outerspace.swipegesturesburger;

import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class SwipeUtils {
    public enum SwipeType {
        SWIPE_RIGHT,
        SWIPE_LEFT,
        SWIPE_UP,
        SWIPE_DOWN
    }

    public static String actionMaskedName(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_BUTTON_PRESS : return "ACTION_BUTTON_PRESS";
            case MotionEvent.ACTION_BUTTON_RELEASE : return "ACTION_BUTTON_RELEASE";
            case MotionEvent.ACTION_CANCEL : return "ACTION_CANCEL";
            case MotionEvent.ACTION_DOWN : return "ACTION_DOWN";
            case MotionEvent.ACTION_HOVER_ENTER : return "ACTION_HOVER_ENTER";
            case MotionEvent.ACTION_HOVER_EXIT : return "ACTION_HOVER_EXIT";
            case MotionEvent.ACTION_HOVER_MOVE : return "ACTION_HOVER_MOVE";
            case MotionEvent.ACTION_MASK : return "ACTION_MASK";
            case MotionEvent.ACTION_MOVE : return "ACTION_MOVE";
            case MotionEvent.ACTION_OUTSIDE : return "ACTION_OUTSIDE";
            case MotionEvent.ACTION_POINTER_DOWN : return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_UP : return "ACTION_POINTER_UP";
            case MotionEvent.ACTION_SCROLL : return "ACTION_SCROLL";
            case MotionEvent.ACTION_UP : return "ACTION_UP";
        }
        return "";
    }

    public static float angleRadians(MotionEvent initial, MotionEvent ending) {
        float x1 = initial.getX();
        float y1 = initial.getY();
        float x2 = ending.getX();
        float y2 = ending.getY();

        float dy = y2 - y1;
        float dx = x2 - x1;
        float slope = dy / dx;
        float alpha = (float) Math.atan(slope);

        if(dx >= 0.0f && dy <  0.0f) { alpha = (float)Math.PI * 2f + alpha;}
        if(dx <  0.0f ) { alpha = (float)Math.PI + alpha;}
        return alpha;
    }

    public static SwipeType swipeType(MotionEvent initial, MotionEvent ending) {
        float angle = angleRadians(initial, ending);
        if(angle >= Math.PI / 4.0f && angle < Math.PI * 3.0f / 4.0f ) return SwipeType.SWIPE_DOWN;
        if(angle >= Math.PI * 3.0f / 4.0f && angle < Math.PI * 5.0f / 4.0f ) return SwipeType.SWIPE_LEFT;
        if(angle >= Math.PI * 5.0f / 4.0f && angle < Math.PI * 7.0f / 4.0f ) return SwipeType.SWIPE_UP;
        return SwipeType.SWIPE_RIGHT;
    }

    public static float swipeLength(MotionEvent initial, MotionEvent ending) {
        float dx = initial.getX() - ending.getX();
        float dy = initial.getY() - ending.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static float swipePercent(DisplayMetrics displayMetrics, MotionEvent initial, MotionEvent ending) {
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;
        float l = swipeLength(initial, ending);
        SwipeType st = swipeType(initial, ending);
        if(st == SwipeType.SWIPE_DOWN || st == SwipeType.SWIPE_UP)
                return l / h;
        else
                return l / w;
    }
}
