package com.sec.android.gallery;

import android.graphics.Bitmap;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageItem {
    private Bitmap image;
    private String name;
    private final String description;

    public ImageItem(Bitmap image, String name, String description) {
        super();
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
