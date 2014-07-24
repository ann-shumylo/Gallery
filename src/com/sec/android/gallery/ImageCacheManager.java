package com.sec.android.gallery;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.sec.android.gallery.interfaces.OnBitmapLoadListener;
import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.Receiver;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageCacheManager {
    LruCache<Long, Bitmap> memoryCache;

    public ImageCacheManager() {
        memoryCache = getMemoryCache();
    }

    private LruCache<Long, Bitmap> getMemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<Long, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(Long key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
        return memoryCache;
    }

    void addBitmapToMemoryCache(Long key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    Bitmap getBitmapFromMemCache(Long key) {
        return memoryCache.get(key);
    }

    public void getImage(final Image imageItem, final OnBitmapLoadListener bitmapLoadListener) {
        if (bitmapLoadListener == null) {
            throw new IllegalArgumentException("listener cannot be null ");
        }
        final Bitmap bitmapFromMemCache = getBitmapFromMemCache(imageItem.getId());

        if (bitmapFromMemCache != null) {
            bitmapLoadListener.onBitmapLoaded(bitmapFromMemCache);
        } else {
            imageItem.getBitmap(new Receiver<Bitmap>() {
                @Override
                public void receive(Bitmap bitmap) {
                    addBitmapToMemoryCache(imageItem.getId(), bitmap);
                    bitmapLoadListener.onBitmapLoaded(bitmap);
                }
            });
        }
    }
}
