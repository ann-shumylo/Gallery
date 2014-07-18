package com.sec.android.gallery.listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.sec.android.gallery.ImageAdapter;
import com.sec.android.gallery.interfaces.Image;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageOnItemClickListener implements AdapterView.OnItemClickListener {
    private Context mContext;
    private ImageAdapter mImageAdapter;

    public ImageOnItemClickListener(Context context, ImageAdapter imageAdapter) {
        this.mContext = context;
        this.mImageAdapter = imageAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int length = Toast.LENGTH_LONG;
        Toast.makeText(mContext, ((Image) mImageAdapter.getItem(position)).getName() + "\n" +
                ((Image) mImageAdapter.getItem(position)).getDescription(), length).show();
    }
}
