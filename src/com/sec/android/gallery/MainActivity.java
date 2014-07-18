package com.sec.android.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.dropbox.client2.DropboxAPI;
import com.marchuk.dropbox.implementation.DropBoxManager;
import com.marchuk.dropbox.implementation.ImageProviderDropBoxImpl;
import com.samsung.image.loader.PicasaImageProvider;
import com.sec.android.gallery.interfaces.Image;
import com.sec.android.gallery.interfaces.ImageProvider;
import com.sec.android.gallery.interfaces.Receiver;
import com.sec.android.gallery.providers.DropBoxImageProvider;
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

    private DropBoxManager dropBoxManager;

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
    private ImageAdapter imageGridAdapter;
    /**
     * Image adapter for the grid view.
     */
    private ImageAdapter imageListAdapter;
    /**
     * Image adapter for the list view.
     */
    private ImageProvider imageProvider;
    private Receiver<Collection<Image>> mReceiver;

    private Mode mMode;

    enum Mode {
        DROP_BOX,
        PICASA
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // Request progress bar
        setContentView(R.layout.main);

        dropBoxManager = new DropBoxManager(MainActivity.this, "wuy7uomebx0kfyj", "ze468z8b8udaiqa");

        if (!dropBoxManager.isLoggedIn()) {
            dropBoxManager.authorize();
        }
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

        sdCardImagesGrid.setOnItemClickListener(new ImageOnItemClickListener(getApplicationContext(), imageGridAdapter));
        sdCardImagesList.setOnItemClickListener(new ImageOnItemClickListener(getApplicationContext(), imageListAdapter));
    }

    @Override
    protected void onResume() {
        super.onResume();
        dropBoxManager.onResumeActivity();
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
        ((ToggleButton) findViewById(R.id.loader)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imageGridAdapter.clean();
                imageGridAdapter.notifyDataSetChanged();
                imageListAdapter.clean();
                imageListAdapter.notifyDataSetChanged();
                if (isChecked) {
                    mMode = Mode.PICASA;
                    imageProvider = new com.sec.android.gallery.providers.PicasaImageProvider(new PicasaImageProvider(new PicasaImageProvider.OnInitializedListener() {
                        @Override
                        public void onInitialized() {
                            imageProvider.getImages(mReceiver);
                        }
                    }));
                } else {
                    mMode = Mode.DROP_BOX;
                    imageProvider = new DropBoxImageProvider(new ImageProviderDropBoxImpl(dropBoxManager.getDropBoxApi(), DropboxAPI.ThumbSize.ICON_256x256));
                    imageProvider.getImages(mReceiver);
                }
            }
        });
    }

    /**
     * Setup the grid view.
     */
    private void setupViews() {
        sdCardImagesGrid = (GridView) findViewById(R.id.gridView);
        sdCardImagesList = (ListView) findViewById(R.id.listView);

        sdCardImagesGrid.setClipToPadding(false);
        sdCardImagesList.setClipToPadding(false);

        imageGridAdapter = new ImageAdapter(this, R.layout.gridview_item);
        sdCardImagesGrid.setAdapter(imageGridAdapter);

        imageListAdapter = new ImageAdapter(this, R.layout.listview_item);
        sdCardImagesList.setAdapter(imageListAdapter);
        sdCardImagesList.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        sdCardImagesGrid.setAdapter(null);
        sdCardImagesList.setAdapter(null);
        imageGridAdapter.clean();
        imageGridAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

    private void retainPositionOfGridView(int countColumns) {
        int index = sdCardImagesGrid.getFirstVisiblePosition();
        sdCardImagesGrid.setNumColumns(countColumns);
        sdCardImagesGrid.setSelection(index);
    }
}






