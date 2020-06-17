package org.fish.silas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.fish.silas.contract.HomeContract;
import org.fish.silas.data.event.CouponCheckedEvent;
import org.fish.silas.data.event.DishClickEvent;
import org.fish.silas.data.event.DishClickEventKt;
import org.fish.silas.data.vm.VMCoupon;
import org.fish.silas.data.vm.VMPaySuccess;
import org.fish.silas.data.vm.VMSingleDish;
import org.fish.silas.model.CouponAdapterImpl;
import org.fish.silas.model.DishAdapterImpl;
import org.fish.silas.model.HomeModel;
import org.fish.silas.presenter.HomePresenter;
import org.fish.silas.utils.Collections;
import org.fish.silas.utils.EventHubs;
import org.fish.silas.utils.Formats;
import org.fish.silas.viewbinder.SingleDishVBinder;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.karic.smartadapter.SmartAdapter;

import java.util.List;

public class HomeActivity extends ZygoteActivity implements HomeContract.IView {
    private SmartAdapter smartAdapter;
    private HomeContract.IPresenter presenter;

    @Override
    protected void initView() {
        super.initView();
        initAdapter();
        setupListener();
        EventHubs.INSTANCE.registerClickReceiver(this);
        presenter = new HomePresenter(this, new HomeModel(new DishAdapterImpl(), new CouponAdapterImpl()));
        presenter.loadProducts();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_home;
    }

    private void initAdapter() {
        smartAdapter = new SmartAdapter();
        smartAdapter.register(VMSingleDish.class, new SingleDishVBinder());
        RecyclerView recyclerView = pieces.find(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(smartAdapter);
    }

    private void setupListener() {
        pieces.find(R.id.tv_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadCoupons();
            }
        });
    }

    @Override
    public void showPayAmount(long amount) {
        TextView textView = pieces.find(R.id.tv_order_amount);
        textView.setText(Formats.INSTANCE.formatCurrency(amount / 100.0));
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
    }

    @Override
    public void showProducts(@NotNull List<? extends VMSingleDish> data) {
        smartAdapter.setData(data);
    }

    @Override
    public void onDestroy() {
        EventHubs.INSTANCE.unregisterClickReceiver(this);
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showError(int code, @NotNull String message) {
        showToast(message);
    }

    @Subscribe
    public void onEvent(DishClickEvent event) {
        switch (event.getAction()) {
            case DishClickEventKt.ADD_CLICK:
                presenter.addShoppingCart(event.getId());
                refreshCheckoutStatus();
                break;

            case DishClickEventKt.SUBTRACT_CLICK:
                presenter.removeShoppingCart(event.getId());
                refreshCheckoutStatus();
                break;
        }
    }

    @Subscribe
    public void onEvent(CouponCheckedEvent event) {
        presenter.pay(event.getCoupons());
    }

    private void refreshCheckoutStatus() {
        pieces.find(R.id.tv_checkout).setEnabled(presenter.getDishCount() > 0);
    }

    @Override
    public void showPayResult(@NonNull VMPaySuccess result) {
        if (result.getSuccess()) {
            showPayAmount(0);
            PaySuccessDialog.Companion.show(getSupportFragmentManager(), result);
        } else {
            showToast("支付失败");
        }
    }

    private void showCouponDialog(List<VMCoupon> coupons) {
        CouponDialog.show(getSupportFragmentManager(), coupons);
    }

    @Override
    public void showCoupons(@NotNull List<VMCoupon> coupons) {
        if (Collections.isEmpty(coupons)) {
            presenter.pay(null);
        } else {
            showCouponDialog(coupons);
        }
    }
}
