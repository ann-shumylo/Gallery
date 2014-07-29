package com.sec.android.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sec.android.gallery.interfaces.OnBitmapLoadListener;
import com.sec.android.gallery.interfaces.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageAdapter extends BaseAdapter {
    private final List<Image> imageItems = new ArrayList<Image>();
    private final Context mContext;
    private final int mLayoutResource;
    private final ImageCacheManager imageCacheManager = new ImageCacheManager();

    public ImageAdapter(Context context, int layoutResource) {
        mContext = context;
        mLayoutResource = layoutResource;
    }

    public void addPhoto(Image imageItem) {
        imageItems.add(imageItem);
    }

    public void addPhoto(int index, Image imageItem) {
        imageItems.add(index, imageItem);
    }

    public void addPhotoList(List<Image> list) {
        imageItems.addAll(list);
    }

    public void clean() {
        imageItems.clear();
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayoutResource, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.imageName = (TextView) convertView.findViewById(R.id.image_name);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Image imageItem = imageItems.get(position);
        holder.imageName.setText(imageItem.getName());
        holder.neededPosition = position;

        holder.imageView.setImageBitmap(null);
        imageCacheManager.getImage(imageItem, new OnBitmapLoadListener() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                if (position == holder.neededPosition) {
                    holder.imageView.setImageBitmap(bitmap);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView imageName;
        int neededPosition;
    }
}