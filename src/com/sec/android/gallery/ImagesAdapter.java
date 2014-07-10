package com.sec.android.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.marchuk.dropbox.Image;
import com.marchuk.dropbox.Receiver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ImagesAdapter extends ArrayAdapter<Image> {
    List<Image> imageItems = new ArrayList<Image>();
    int layoutResource;

    public ImagesAdapter(Context context, int resource) {
        super(context, resource, new ArrayList<Image>());
        this.layoutResource = resource;
    }

    public void addPhoto(Image imageItem) {
        imageItems.add(imageItem);
    }

    public void addPhotoList(List<Image> list) {
        imageItems = list;
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public Image getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.imageName = (TextView) convertView.findViewById(R.id.image_name);
            holder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Image item = imageItems.get(position);
        holder.imageName.setText(item.getName());

        item.getBitmap(new Receiver<Bitmap>() {
            @Override
            public void receive(Bitmap value) {
                holder.image.setImageBitmap(value);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView imageName;
    }
}
