package com.example.gymvision.overlay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class AutoTextureView extends TextureView {

    public AutoTextureView(final Context context) {
        this(context, null);
    }
    public AutoTextureView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextureView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

}