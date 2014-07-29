package com.sec.android.gallery.interfaces;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public interface ImageProvider {

    /**
     * Send retrieved images to reciever by {@link  Receiver#receive(Object)} Long
     * time operation.
     *
     * @param receiver send result to
     */
    void getImages(Receiver<Collection<Image>> receiver);
}
