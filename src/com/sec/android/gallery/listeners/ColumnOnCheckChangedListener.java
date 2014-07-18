package com.sec.android.gallery.listeners;

import android.widget.RadioGroup;
import android.widget.ToggleButton;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public class ColumnOnCheckChangedListener implements RadioGroup.OnCheckedChangeListener {
    /**
     * The listener for a checked change event of the toggle buttons.
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //if one button is checked, uncheck all others
        for (int j = 0; j < group.getChildCount(); j++) {
            final ToggleButton view = (ToggleButton) group.getChildAt(j);
            view.setChecked(view.getId() == checkedId);
        }
    }
}
