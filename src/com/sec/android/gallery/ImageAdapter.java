package com.sec.android.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sec.android.gallery.R;
import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.Receiver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImageAdapter extends BaseAdapter {
    List<Image> imageItems = new ArrayList<Image>();
    private Context mContext;
    private int mLayoutResource;

    public ImageAdapter(Context context, int layoutResource) {
        this.mContext = context;
        this.mLayoutResource = layoutResource;
    }

//    public void addPhoto(Image imageItem) {
//        imageItems.add(imageItem);
//    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
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
        Image imageItem = imageItems.get(position);
        holder.imageName.setText(imageItem.getName());
        holder.imageView.setImageBitmap(null);
        final ViewHolder finalHolder = holder;
        final int requestPosition = position;
        holder.neededPosition = position;
        imageItem.getBitmap(new Receiver<Bitmap>() {
            @Override
            public void receive(Bitmap bitmap) {
                if (requestPosition == finalHolder.neededPosition) {
                    finalHolder.imageView.setImageBitmap(bitmap);
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

//private SparseArray<View> viewsList = new SparseArray<View>(); //private Map<Integer, View> myViews = new HashMap<Integer, View>();
//View gridView = viewsList.get(position);

//        viewsList.put(position, gridView);
//        if (viewsList.size() == 50) {
//            viewsList.clear();
//        }