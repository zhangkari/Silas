package org.fish.silas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        int menuId = getMenuLayout();
        if (menuId != 0) {
            toolbar.inflateMenu(menuId);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onMenuClick(item);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationClick();
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
        setCancelable(false);
    }

    protected void setTitle(@StringRes int resId) {
        toolbar.setTitle(resId);
    }

    protected void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    protected boolean onMenuClick(MenuItem menu) {
        return false;
    }

    protected void onNavigationClick() {
        dismiss();
    }

    protected void init() {

    }

    protected void showProgress() {
        rootView.findViewById(R.id.layout_progress).setVisibility(View.VISIBLE);
    }

    protected void hideProgress() {
        rootView.findViewById(R.id.layout_progress).setVisibility(View.GONE);
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
