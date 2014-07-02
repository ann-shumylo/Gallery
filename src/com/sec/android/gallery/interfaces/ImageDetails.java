package com.sec.android.gallery.interfaces;

import android.graphics.Bitmap;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public interface ImageDetails {
    public Bitmap getImage();

    String getName();

    String getDescription();

    public long getId();

    Collection<String> getCategories();

    float getRating();
}
