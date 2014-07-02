package com.sec.android.gallery;

import android.graphics.Bitmap;
import com.sec.android.gallery.interfaces.ImageDetails;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageItem implements ImageDetails {
    private Bitmap image;
    private String name;
    private final String description;

    public ImageItem(Bitmap image, String name, String description) {
        super();
        this.image = image;
        this.name = name;
        this.description = description;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public Collection<String> getCategories() {
        return null;
    }

    @Override
    public float getRating() {
        return 0;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
