package com.sec.android.gallery;

import android.graphics.Bitmap;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
class ImageItem {
    private Bitmap image;
    private String title;
    private final String description;

    public ImageItem(Bitmap image, String title, String description) {
        super();
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
