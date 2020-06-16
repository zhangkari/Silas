package org.fish.silas;

import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.karic.smartadapter.SmartAdapter;

public class CouponDialog extends ZygoteDialog {
    private static final String TAG = "CouponDialog";

    private SmartAdapter adapter;

    public static void show(FragmentManager fragmentManager) {
        new CouponDialog().show(fragmentManager, TAG);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.dialog_coupon;
    }

    @Override
    protected void init() {
        adapter = new SmartAdapter();
        RecyclerView recyclerView = rootView.findViewById(R.id.coupons);
        recyclerView.setAdapter(adapter);
        // todo
    }

    @Override
    protected void resetStyle() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8f);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setDimAmount(0.5f);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }
}
