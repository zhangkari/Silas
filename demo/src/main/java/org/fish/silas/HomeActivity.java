package org.fish.silas;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.fish.silas.contract.HomeContract;
import org.fish.silas.data.event.DishClickEvent;
import org.fish.silas.data.event.DishClickEventKt;
import org.fish.silas.data.vm.VMSingleDish;
import org.fish.silas.model.HomeAdapterImpl;
import org.fish.silas.model.HomeModel;
import org.fish.silas.presenter.HomePresenter;
import org.fish.silas.utils.Counters;
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
        presenter = new HomePresenter(this, new HomeModel(new HomeAdapterImpl()));
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
                presenter.pay();
            }
        });
    }

    @Override
    public void showPayAmount(long amount) {
        TextView textView = pieces.find(R.id.tv_order_amount);
        textView.setText(Formats.INSTANCE.formatAmount(amount / 100.0));
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
                Counters.INSTANCE.increase();
                presenter.addProduct(event.getId());
                refreshCheckoutStatus();
                break;

            case DishClickEventKt.SUBTRACT_CLICK:
                Counters.INSTANCE.decrease();
                presenter.removeProduct(event.getId());
                refreshCheckoutStatus();
                break;
        }
    }

    private void refreshCheckoutStatus() {
        pieces.find(R.id.tv_checkout).setEnabled(Counters.INSTANCE.getCount() > 0);
    }

    @Override
    public void showPayResult(boolean success) {
        if (success) {
            showToast("支付成功");
        } else {
            showToast("支付失败");
        }
    }
}
