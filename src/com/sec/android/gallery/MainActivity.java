package com.sec.android.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.marchuk.dropbox.Image;
import com.marchuk.dropbox.Receiver;
import com.marchuk.dropbox.implementation.DropBoxManager;
import com.marchuk.dropbox.implementation.ImageProviderDropBoxImpl;

import java.util.Collection;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private RadioGroup columns;
    private ToggleButton twoColumns;
    private ToggleButton threeColumns;
    private ToggleButton fiveColumns;

    DropBoxManager dropBoxManager;
    ImageProviderDropBoxImpl imageProviderDropBox;

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

        dropBoxManager = new DropBoxManager(MainActivity.this, "kqu8ru6p67widos", "gdpr4jk2lp9emgd");

        if (!dropBoxManager.isLoggedIn()) {
            dropBoxManager.authorize();
        }

        setupViews();
        imageProviderDropBox = new ImageProviderDropBoxImpl(dropBoxManager.getDropBoxApi());
        imageProviderDropBox.getImages(new Receiver<Collection<Image>>() {
            @Override
            public void receive(Collection<Image> data) {
                for (Image imageItem : data) {
                    imageGridAdapter.addPhoto(imageItem);
                    imageGridAdapter.notifyDataSetChanged();
                    imageListAdapter.addPhoto(imageItem);
                    imageListAdapter.notifyDataSetChanged();
                }
            }
        });

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
                    sdCardImagesGrid.setSelection(sdCardImagesList.getFirstVisiblePosition());
                    columns.setVisibility(View.VISIBLE);
                    sdCardImagesGrid.setVisibility(View.VISIBLE);
                    sdCardImagesList.setVisibility(View.GONE);
                } else {
                    sdCardImagesList.setSelection(sdCardImagesGrid.getFirstVisiblePosition());
                    columns.setVisibility(View.GONE);
                    sdCardImagesGrid.setVisibility(View.GONE);
                    sdCardImagesList.setVisibility(View.VISIBLE);
                }
            }
        });

        sdCardImagesGrid.setOnItemClickListener(this);
        sdCardImagesList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dropBoxManager.onResumeActivity();
    }

    /**
     * The listener for a checked change event of the toggle buttons.
     */
    private static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
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
        int length = Toast.LENGTH_LONG;
        Toast.makeText(getBaseContext(), imageGridAdapter.getItem(position).getName() + "\n" +
                imageGridAdapter.getItem(position).getDescription(), length).show();
    }

    @Override
    public void onClick(View v) {
        if (v == twoColumns || v == threeColumns || v == fiveColumns) {
            columns.clearCheck();
            columns.check(v.getId());
        }

        switch (v.getId()) {
            case R.id.toggle_two_columns:
                retainPositionOfGridView(2);
                break;
            case R.id.toggle_three_columns:
                retainPositionOfGridView(3);
                break;
            case R.id.toggle_five_columns:
                retainPositionOfGridView(5);
                break;
            default:
                break;
        }
    }

    private void retainPositionOfGridView(int countColumns) {
        int index = sdCardImagesGrid.getFirstVisiblePosition();
        sdCardImagesGrid.setNumColumns(countColumns);
        sdCardImagesGrid.setSelection(index);
    }
}