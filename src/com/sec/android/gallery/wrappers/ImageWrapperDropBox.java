package com.sec.android.gallery.wrappers;

import android.graphics.Bitmap;
import com.sec.android.gallery.interfaces.Image;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageWrapperDropBox implements Image<com.marchuk.dropbox.Receiver> {
    com.marchuk.dropbox.Image mDropBoxImage;

    public ImageWrapperDropBox(com.marchuk.dropbox.Image dropBoxImage) {
        this.mDropBoxImage = dropBoxImage;
    }

    @Override
    public long getId() {
        return mDropBoxImage.getId();
    }

    @Override
    public void getBitmap(final com.sec.android.gallery.interfaces.Receiver receiver) {
        mDropBoxImage.getBitmap(new com.marchuk.dropbox.Receiver<Bitmap>() {
            @Override
            public void receive(Bitmap bitmap) {
                receiver.receive(bitmap);
            }
        });
    }

    @Override
    public String getName() {
        return mDropBoxImage.getName();
    }

    @Override
    public Collection<String> getCategories() {
        return mDropBoxImage.getCategories();
    }

    @Override
    public String getDescription() {
        return mDropBoxImage.getDescription();
    }
}
