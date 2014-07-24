package com.sec.android.gallery;

import android.os.Bundle;
import com.samsung.image.loader.PicasaImageProvider;
import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.ImageProvider;
import com.sec.android.gallery.interfaces.Receiver;

import java.util.Collection;
import java.util.Iterator;


/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class PicasaActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageProvider = new com.sec.android.gallery.providers.PicasaImageProvider(new PicasaImageProvider(new PicasaImageProvider.OnInitializedListener() {
            @Override
            public void onInitialized() {
                mProgressDialog.dismiss();
                imageProvider.getImages(mReceiver);
            }
        }));
    }
}






