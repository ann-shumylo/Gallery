package com.sec.android.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.ImageProvider;
import com.sec.android.gallery.interfaces.Receiver;
import com.sec.android.gallery.listeners.ColumnOnCheckChangedListener;
import com.sec.android.gallery.listeners.ImageOnItemClickListener;

import java.util.Collection;
import java.util.Iterator;


/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class MainActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private RadioGroup columns;
    private ToggleButton twoColumns;
    private ToggleButton threeColumns;
    private ToggleButton fiveColumns;

    /**
     * Grid view holding the images.
     */
    private GridView imagesGridView;
    /**
     * List view holding the images.
     */
    private ListView imagesListView;
    /**
     * Image adapter for the grid view.
     */
    private ImageAdapter imageGridAdapter;
    /**
     * Image adapter for the list view.
     */
    private ImageAdapter imageListAdapter;

    ImageProvider imageProvider;
    Receiver<Collection<Image>> mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // Request progress bar
        setContentView(R.layout.main);

        setupViews();

        twoColumns = (ToggleButton) findViewById(R.id.toggle_two_columns);
        threeColumns = (ToggleButton) findViewById(R.id.toggle_three_columns);
        fiveColumns = (ToggleButton) findViewById(R.id.toggle_five_columns);
        twoColumns.setOnClickListener(this);
        threeColumns.setOnClickListener(this);
        fiveColumns.setOnClickListener(this);

        ((ToggleButton) findViewById(R.id.modes)).setOnCheckedChangeListener(this);

        columns = (RadioGroup) findViewById(R.id.toggle_group);
        columns.setOnCheckedChangeListener(new ColumnOnCheckChangedListener());

        imagesGridView.setOnItemClickListener(new ImageOnItemClickListener(getApplicationContext(), imageGridAdapter));
        imagesListView.setOnItemClickListener(new ImageOnItemClickListener(getApplicationContext(), imageListAdapter));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new Receiver<Collection<Image>>() {
            @Override
            public void receive(Collection<Image> data) {
                Iterator<Image> iterator = data.iterator();
                for (int i = 0; i < Math.min(data.size(), 30); i++) {
                    imageGridAdapter.addPhoto(i, iterator.next());
                    imageGridAdapter.notifyDataSetChanged();
                    imageListAdapter.addPhoto(i, iterator.next());
                    imageListAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    /**
     * Setup the grid view.
     */
    private void setupViews() {
        imagesGridView = (GridView) findViewById(R.id.gridView);
        imagesListView = (ListView) findViewById(R.id.listView);

        imagesGridView.setClipToPadding(false);
        imagesListView.setClipToPadding(false);

        imageGridAdapter = new ImageAdapter(this, R.layout.gridview_item);
        imagesGridView.setAdapter(imageGridAdapter);

        imageListAdapter = new ImageAdapter(this, R.layout.listview_item);
        imagesListView.setAdapter(imageListAdapter);
        imagesListView.setVisibility(View.GONE);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            imagesGridView.setSelection(imagesListView.getFirstVisiblePosition());
            columns.setVisibility(View.VISIBLE);
            imagesGridView.setVisibility(View.VISIBLE);
            imagesListView.setVisibility(View.GONE);
        } else {
            imagesListView.setSelection(imagesGridView.getFirstVisiblePosition());
            columns.setVisibility(View.GONE);
            imagesGridView.setVisibility(View.GONE);
            imagesListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        imagesGridView.setAdapter(null);
        imagesListView.setAdapter(null);
        imageGridAdapter.clean();
        imageGridAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

    private void retainPositionOfGridView(int countColumns) {
        int index = imagesGridView.getFirstVisiblePosition();
        imagesGridView.setNumColumns(countColumns);
        imagesGridView.setSelection(index);
    }
}






