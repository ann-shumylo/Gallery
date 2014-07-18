package com.sec.android.gallery.wrappers;

import android.graphics.Bitmap;
import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.Receiver;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageWrapperPicasa implements Image<com.samsung.image.loader.Receiver> {
    com.samsung.image.loader.Image mPicasaImage;

    public ImageWrapperPicasa(com.samsung.image.loader.Image picasaImage) {
        this.mPicasaImage = picasaImage;
    }

    @Override
    public long getId() {
        return mPicasaImage.getId();
    }

    @Override
    public void getBitmap(final Receiver receiver) {
        mPicasaImage.getBitmap(new com.samsung.image.loader.Receiver<Bitmap>() {
            @Override
            public void receive(Bitmap bitmap) {
                receiver.receive(bitmap);
            }
        });
    }

    @Override
    public String getName() {
        return mPicasaImage.getName();
    }

    @Override
    public Collection<String> getCategories() {
        return mPicasaImage.getCategories();
    }

    @Override
    public String getDescription() {
        return mPicasaImage.getDescription();
    }
}
