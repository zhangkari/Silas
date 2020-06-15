package org.fish.silas;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.fish.silas.utils.Pieces;

public class ZygoteActivity extends AppCompatActivity {
    protected Pieces pieces;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_zygote);
        pieces = new Pieces(findViewById(android.R.id.content));
        int layoutId = getContentLayout();
        if (layoutId != 0) {
            View.inflate(this, getContentLayout(), (ViewGroup) pieces.find(R.id.layout_content));
        }
        initView();
    }

    protected int getContentLayout() {
        return 0;
    }

    protected void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = pieces.find(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        if (menu.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menu);
    }

    protected void showLoading() {
        pieces.setVisibility(R.id.layout_progress, View.VISIBLE);
    }

    protected void dismissLoading() {
        pieces.setVisibility(R.id.layout_progress, View.GONE);
    }

    protected void showEmptyView() {
        pieces.setVisibility(R.id.layout_empty, View.VISIBLE);
    }

    protected void dismissEmptyView() {
        pieces.setVisibility(R.id.layout_empty, View.GONE);
    }

    protected void showToast(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(@StringRes int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }
}