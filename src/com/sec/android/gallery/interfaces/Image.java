package com.sec.android.gallery.interfaces;

import java.util.Collection;

/**
 * Describes Image from data source.
 *
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public interface Image<Receiver> {
    /**
     * @return id of image
     */
    long getId();
    /**
     * Get bitmap for image Long time operation.
     *
     * @param receiver for result. Will call {@link com.sec.android.gallery.interfaces.Receiver#receive(Object)}
     */
    void getBitmap(com.sec.android.gallery.interfaces.Receiver<Receiver> receiver);
    /**
     * @return name of image
     */
    String getName();
    /**
     * @return categories of image.
     */
    Collection<String> getCategories();
    /**
     * @return description of image
     */
    String getDescription();
}
