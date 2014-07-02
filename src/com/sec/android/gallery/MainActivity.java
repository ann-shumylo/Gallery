package com.sec.android.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.sec.android.gallery.interfaces.LoaderListener;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, LoaderListener<ImageItem> {
    private RadioGroup columns;
    private ToggleButton twoColumns;
    private ToggleButton threeColumns;
    private ToggleButton fiveColumns;

    /**
     * Grid view holding the images.
     */
    private GridView sdCardImagesGrid;
    /**
     * List view holding the images.
     */
    private ListView sdCardImagesList;
    /**
     * Image adapter for the grid view.
     */
    private ImagesAdapter imageGridAdapter;
    /**
     * Image adapter for the list view.
     */
    private ImagesAdapter imageListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // Request progress bar
        setContentView(R.layout.main);

        setupViews();
        new Receiver(this, this).execute();

        twoColumns = (ToggleButton) findViewById(R.id.toggle_two_columns);
        threeColumns = (ToggleButton) findViewById(R.id.toggle_three_columns);
        fiveColumns = (ToggleButton) findViewById(R.id.toggle_five_columns);
        twoColumns.setOnClickListener(this);
        threeColumns.setOnClickListener(this);
        fiveColumns.setOnClickListener(this);

        columns = (RadioGroup) findViewById(R.id.toggle_group);
        columns.setOnCheckedChangeListener(ToggleListener);

        findViewById(R.id.modes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) findViewById(R.id.modes)).isChecked()) {
                    columns.setVisibility(View.VISIBLE);
                    sdCardImagesGrid.setVisibility(View.VISIBLE);
                    sdCardImagesList.setVisibility(View.GONE);
                } else {
                    columns.setVisibility(View.GONE);
                    sdCardImagesGrid.setVisibility(View.GONE);
                    sdCardImagesList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * The listener for a checked change event of the toggle buttons.
     */
    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            //if one button is checked, uncheck all others
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };

    /**
     * Setup the grid view.
     */
    private void setupViews() {
        sdCardImagesGrid = (GridView) findViewById(R.id.gridView);
        sdCardImagesList = (ListView) findViewById(R.id.listView);

        sdCardImagesGrid.setClipToPadding(false);
        sdCardImagesList.setClipToPadding(false);

        imageGridAdapter = new ImagesAdapter(getApplicationContext(), R.layout.gridview_item);
        sdCardImagesGrid.setAdapter(imageGridAdapter);

        imageListAdapter = new ImagesAdapter(getApplicationContext(), R.layout.listview_item);
        sdCardImagesList.setAdapter(imageListAdapter);
        sdCardImagesList.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        if (v == twoColumns || v == threeColumns || v == fiveColumns) {
            columns.clearCheck();
            columns.check(v.getId());
        }

        switch (v.getId()) {
            case R.id.toggle_two_columns:
                sdCardImagesGrid.setNumColumns(2);
                break;
            case R.id.toggle_three_columns:
                sdCardImagesGrid.setNumColumns(3);
                break;
            case R.id.toggle_five_columns:
                sdCardImagesGrid.setNumColumns(5);
                break;
            default:
                break;
        }
    }

    @Override
    public void receive(ImageItem value) {
        imageGridAdapter.addPhoto(value);
        imageGridAdapter.notifyDataSetChanged();

        imageListAdapter.addPhoto(value);
        imageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideProgress() {
        setProgressBarIndeterminateVisibility(false);
    }
}