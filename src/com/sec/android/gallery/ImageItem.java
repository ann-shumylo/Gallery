package com.sec.android.gallery;

import android.graphics.Bitmap;
import com.sec.android.gallery.interfaces.ImageDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageItem implements ImageDetails {
    private long mId;
    private Bitmap mImage;
    private String mName;
    private final String mDescription;
    private List<String> categories = new ArrayList<String>();

    public ImageItem(Bitmap image, String name, String description) {
        super();
        this.mImage = image;
        this.mName = name;
        this.mDescription = description;
    }

    @Override
    public Bitmap getImage() {
        return mImage;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public Collection<String> getCategories() {
        return categories;
    }

    @Override
    public float getRating() {
        return 0;
    }

    public void setImage(Bitmap image) {
        this.mImage = image;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
