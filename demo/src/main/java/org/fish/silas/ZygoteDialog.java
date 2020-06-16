package org.fish.silas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

public class ZygoteDialog extends DialogFragment {
    protected Toolbar toolbar;
    protected View rootView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        return inflater.inflate(R.layout.activity_zygote, parent, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        rootView = view;
        initToolbar();

        int layoutId = getContentLayout();
        if (layoutId != 0) {
            View.inflate(getContext(), layoutId, (ViewGroup) rootView.findViewById(R.id.layout_content));
        }
        init();
    }

    private void initToolbar() {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.inflateMenu(getMenuLayout());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onMenuClick(item);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        resetStyle();
    }

    protected int getContentLayout() {
        return 0;
    }

    protected int getMenuLayout() {
        return R.menu.menu_zygote_dialog;
    }

    protected void resetStyle() {

    }

    protected void setTitle(@StringRes int resId) {
        toolbar.setTitle(resId);
    }

    protected void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    protected boolean onMenuClick(MenuItem menu) {
        if (menu.getItemId() == android.R.id.home) {
            dismiss();
            return true;
        }
        return false;
    }

    protected void init() {

    }
}
