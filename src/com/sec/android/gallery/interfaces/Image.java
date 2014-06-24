package com.sec.android.gallery.interfaces;

import android.graphics.Bitmap;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public interface Image {
    public long getId();

    void getBitMap(Receiver<Bitmap> receiver);

    String getName();

    Collection<String> getCategories();

    String getDescription();

    float getRaiting();
}
