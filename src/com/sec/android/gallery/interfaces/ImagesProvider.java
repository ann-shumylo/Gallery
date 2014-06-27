package com.sec.android.gallery.interfaces;

import android.media.Image;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public interface ImagesProvider {
    long getImagesCount();

    void getImage(long startIndex, int window, LoaderListener<Collection<Image>> receiver);
}
