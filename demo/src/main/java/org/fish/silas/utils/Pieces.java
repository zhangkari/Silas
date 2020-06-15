package org.fish.silas.utils;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;

public final class Pieces {
    private View root;
    private SparseArray<View> cache = new SparseArray<>(64);

    public Pieces(View view) {
        root = view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T find(@IdRes int id) {
        View view = cache.get(id);
        if (view != null) {
            return (T) view;
        }
        view = root.findViewById(id);
        if (view != null) {
            cache.put(id, view);
        }
        return (T) view;
    }

    public void setVisibility(@IdRes int id, int visibility) {
        find(id).setVisibility(visibility);
    }
}
