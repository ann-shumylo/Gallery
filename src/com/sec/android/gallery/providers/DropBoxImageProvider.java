package com.sec.android.gallery.providers;

import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.ImageProvider;
import com.sec.android.gallery.interfaces.Receiver;
import com.sec.android.gallery.wrappers.ImageWrapperDropBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class DropBoxImageProvider implements ImageProvider {

    private com.marchuk.dropbox.implementation.ImageProviderDropBoxImpl mProvider;

    public DropBoxImageProvider(com.marchuk.dropbox.implementation.ImageProviderDropBoxImpl provider) {
        this.mProvider = provider;
    }

    @Override
    public void getImages(final Receiver<Collection<Image>> receiver) {
           mProvider.getImages(new com.marchuk.dropbox.Receiver<Collection<com.marchuk.dropbox.Image>>() {
               @Override
               public void receive(Collection<com.marchuk.dropbox.Image> data) {
                   ArrayList<Image> imageList = new ArrayList<Image>();
                   for (com.marchuk.dropbox.Image image : data) {
                       imageList.add(new ImageWrapperDropBox(image));
                   }
                   receiver.receive(imageList);
               }
           });
    }
}
