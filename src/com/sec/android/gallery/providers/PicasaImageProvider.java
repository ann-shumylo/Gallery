package com.sec.android.gallery.providers;

import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.ImageProvider;
import com.sec.android.gallery.interfaces.Receiver;
import com.sec.android.gallery.wrappers.ImageWrapperPicasa;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class PicasaImageProvider implements ImageProvider {

    private final com.samsung.image.loader.PicasaImageProvider mProvider;

    public PicasaImageProvider(com.samsung.image.loader.PicasaImageProvider provider) {
        this.mProvider = provider;
    }

    @Override
    public void getImages(final Receiver<Collection<Image>> receiver) {
           mProvider.getImages(new com.samsung.image.loader.Receiver<Collection<com.samsung.image.loader.Image>>() {
               @Override
               public void receive(Collection<com.samsung.image.loader.Image> images) {
                   ArrayList<Image> imageList = new ArrayList<Image>();
                   for (com.samsung.image.loader.Image image : images) {
                       imageList.add(new ImageWrapperPicasa(image));
                   }
                   receiver.receive(imageList);
               }
           });
    }
}
