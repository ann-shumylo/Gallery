package com.sec.android.gallery;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import com.sec.android.gallery.interfaces.LoaderListener;

import java.io.IOException;

/**
* @author Ganna Pliskovska(g.pliskovska@samsung.com)
*
* Async task for loading the images from the SD card.
*/
public class Receiver extends AsyncTask<Void, ImageItem, Void> {
    private final LoaderListener<ImageItem> loaderListener;

    public Receiver(LoaderListener<ImageItem> loaderListener, Context mContext) {
        this.loaderListener = loaderListener;
        this.mContext = mContext;
    }

    private final Context mContext;

    @Override
    protected Void doInBackground(Void... params) {
        Bitmap bitmap;
        Bitmap newBitmap;
        Uri uri;

        // Set up an array of the Thumbnail Image ID column we want
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.TITLE};

        // Create the cursor pointing to the SDCard
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, // Which columns to return
                null,       // Return all rows
                null,
                null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int size = cursor.getCount();
        int imageID;
        getName(cursor);
        for (int i = 0; i < size; i++) {
            cursor.moveToPosition(i);
            imageID = cursor.getInt(columnIndex);
            uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + imageID);
            try {
                bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
                if (bitmap != null) {
                    newBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    bitmap.recycle();
                    if (newBitmap != null) {
                        publishProgress(new ImageItem(newBitmap, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)), ""));
                    }
                }
            } catch (IOException e) {
                //Error fetching image, try to recover
            }
        }
        cursor.close();
        return null;
    }

    private void getName(Cursor cursor) {
        int count = cursor.getColumnCount();

        for (int i = 0; i < count; i++) {
            cursor.getColumnName(i);
            Log.d("RRR", i + " " + cursor.getColumnName(i));
        }
    }

    /**
     * Add a new LoadedImage in the images grid.
     *
     * @param value The image.
     */
    @Override
    public void onProgressUpdate(ImageItem... value) {
        loaderListener.receive(value[0]);
    }

    /**
     * Set the visibility of the progress bar to false.
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(Void result) {
        loaderListener.hideProgress();
    }

    @Override
    protected void onPreExecute() {
        loaderListener.showProgress();
    }
}