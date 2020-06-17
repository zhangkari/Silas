package org.fish.silas;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.fish.silas.data.vm.VMCoupon;
import org.fish.silas.utils.Collections;
import org.fish.silas.utils.EventHubs;
import org.fish.silas.viewbinder.CouponVBinder;
import org.karic.smartadapter.SmartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponDialog extends ZygoteDialog {
    private static final String TAG = "CouponDialog";

    private List<VMCoupon> coupons;

    public static void show(FragmentManager fragmentManager, List<VMCoupon> data) {
        CouponDialog.newInstance(data).show(fragmentManager, TAG);
    }

    private static CouponDialog newInstance(List<VMCoupon> data) {
        CouponDialog dialog = new CouponDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("coupons", new ArrayList<>(data));
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.dialog_coupon;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        RecyclerView recyclerView = rootView.findViewById(R.id.coupons);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SmartAdapter adapter = new SmartAdapter();
        adapter.register(VMCoupon.class, new CouponVBinder());
        recyclerView.setAdapter(adapter);

        Bundle argument = getArguments();
        if (argument != null) {
            coupons = (List<VMCoupon>) argument.getSerializable("coupons");
            adapter.setData(coupons);
        }
    }

    @Override
    protected void resetStyle() {
        super.resetStyle();

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
    }

    @Override
    protected boolean onMenuClick(MenuItem menu) {
        if (menu.getItemId() == R.id.menu_ok) {
            List<String> list = getCheckedCoupons();
            if (!Collections.isEmpty(list)) {
                EventHubs.INSTANCE.postCheckCouponEvent(list);
            }
            dismiss();
            return true;
        }
        return false;
    }

    private List<String> getCheckedCoupons() {
        List<String> list = new ArrayList<>();
        for (VMCoupon c : coupons) {
            if (c.getChecked()) {
                list.add(c.getId());
            }
        }
        return list;
    }
}
