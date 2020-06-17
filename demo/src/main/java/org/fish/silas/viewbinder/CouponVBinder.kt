package org.fish.silas.viewbinder

import android.view.ViewGroup
import android.widget.CheckBox
import org.fish.silas.R
import org.fish.silas.data.vm.VMCoupon
import org.karic.smartadapter.ViewBinder

class CouponVBinder : ViewBinder<VMCoupon>(R.layout.vb_coupon) {
    private lateinit var data: VMCoupon

    override fun onCreate(parent: ViewGroup?) {
        super.onCreate(parent)
        find<CheckBox>(R.id.cb_coupon).setOnCheckedChangeListener { _, isChecked ->
            data.checked = isChecked
        }
    }

    override fun bindData(data: VMCoupon?) {
        this.data = data!!
        setText(R.id.tv_coupon_type, data.type)
        setText(R.id.tv_coupon_name, data.name)
        find<CheckBox>(R.id.cb_coupon).isChecked = data.checked
    }
}