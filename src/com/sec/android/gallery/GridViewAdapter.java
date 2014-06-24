package com.sec.android.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
class GridViewAdapter extends ArrayAdapter<ImageItem> {
    private int layoutResourceId;

    public GridViewAdapter(Context context, ArrayList<ImageItem> list) {
        super(context, 0, list);
        this.layoutResourceId = R.layout.gridview_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageName = (TextView) row.findViewById(R.id.image_name);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
            row.forceLayout();
        }

        ImageItem item = getItem(position);
        holder.imageName.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageName;
        ImageView image;
    }
}
