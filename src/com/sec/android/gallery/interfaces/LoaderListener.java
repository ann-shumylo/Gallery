package com.sec.android.gallery.interfaces;

/**
 * @author Ganna Pliskovska(g.pliskovska@samsung.com)
 */
public interface LoaderListener<T> {
    void receive(T value);

    void showProgress();

    void hideProgress();
}
