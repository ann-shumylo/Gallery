package com.sec.android.gallery;

import android.os.Bundle;
import com.dropbox.client2.DropboxAPI;
import com.marchuk.dropbox.implementation.DropBoxManager;
import com.marchuk.dropbox.implementation.ImageProviderDropBoxImpl;
import com.sec.android.gallery.providers.DropBoxImageProvider;


/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class DropBoxActivity extends MainActivity {
    private DropBoxManager dropBoxManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dropBoxManager = new DropBoxManager(DropBoxActivity.this, "wuy7uomebx0kfyj", "ze468z8b8udaiqa");

        if (!dropBoxManager.isLoggedIn()) {
            dropBoxManager.authorize();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dropBoxManager.onResumeActivity();
        mImageProvider = new DropBoxImageProvider(new ImageProviderDropBoxImpl(dropBoxManager.getDropBoxApi(), DropboxAPI.ThumbSize.ICON_256x256));
                    mImageProvider.getImages(mReceiver);
    }
}






