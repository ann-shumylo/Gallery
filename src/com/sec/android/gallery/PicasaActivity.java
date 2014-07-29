package com.sec.android.gallery;

import android.os.Bundle;
import com.samsung.image.loader.PicasaImageProvider;


/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class PicasaActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageProvider = new com.sec.android.gallery.providers.PicasaImageProvider(new PicasaImageProvider(new PicasaImageProvider.OnInitializedListener() {
            @Override
            public void onInitialized() {
                mImageProvider.getImages(mReceiver);
            }
        }));
    }
}






