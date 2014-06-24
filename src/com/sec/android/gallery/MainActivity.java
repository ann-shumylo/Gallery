package com.sec.android.gallery;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private ToggleButton twoColumns;
    private ToggleButton threeColumns;
    private ToggleButton fiveColumns;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(this, getData()));

        twoColumns = (ToggleButton) findViewById(R.id.toggle_two_columns);
        threeColumns = (ToggleButton) findViewById(R.id.toggle_three_columns);
        fiveColumns = (ToggleButton) findViewById(R.id.toggle_five_columns);

        twoColumns.setOnClickListener(this);
        threeColumns.setOnClickListener(this);
        fiveColumns.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast toast = Toast.makeText(getApplicationContext(), "bla bla bla", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        // retrieve String drawable array
        TypedArray imagesArray = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imagesArray.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imagesArray.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i, "Descr#" + i));
        }

        return imageItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toggle_two_columns:
                threeColumns.setChecked(false);
                fiveColumns.setChecked(false);

                gridView.setNumColumns(2);
                break;
            case R.id.toggle_three_columns:
                twoColumns.setChecked(false);
                fiveColumns.setChecked(false);

                gridView.setNumColumns(3);
                break;
            case R.id.toggle_five_columns:
                twoColumns.setChecked(false);
                threeColumns.setChecked(false);

                gridView.setNumColumns(5);
                break;
            default:
                break;
        }
    }
}
