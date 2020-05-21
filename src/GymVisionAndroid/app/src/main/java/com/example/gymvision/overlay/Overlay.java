package com.example.gymvision.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public class Overlay extends View {
    private DrawCallback callback;

    public Overlay(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public interface DrawCallback {
        void drawCallback(final Canvas canvas);
    }

    public void setCallback(final DrawCallback callback) {
        this.callback = callback;
    }

    @Override
    public synchronized void draw(final Canvas canvas) {
        super.draw(canvas);
        if(callback != null) {
            callback.drawCallback(canvas);
        }
    }
}