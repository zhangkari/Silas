package org.fish.silas.viewbinder

import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import org.fish.silas.R
import org.fish.silas.data.vm.VMSingleDish
import org.fish.silas.utils.EventHubs
import org.fish.silas.utils.Formats
import org.karic.smartadapter.ViewBinder

class SingleDishVBinder : ViewBinder<VMSingleDish>(R.layout.vb_single_dish) {
    lateinit var data: VMSingleDish

    override fun onCreate(parent: ViewGroup?) {
        super.onCreate(parent)

        setOnClickListener(R.id.tv_dish_add) {
            val text = find<TextView>(R.id.tv_dish_count).text.toString()
            var count = Integer.parseInt(text)
            count++
            data.count = count
            setText(R.id.tv_dish_count, count.toString())
            EventHubs.postAddDishEvent(data.id)
        }

        setOnClickListener(R.id.tv_dish_subtract) {
            val text = find<TextView>(R.id.tv_dish_count).text.toString()
            var count = Integer.parseInt(text)
            if (count >= 1) {
                count--
                data.count = count
                setText(R.id.tv_dish_count, count.toString())
                EventHubs.postSubtractDishEvent(data.id)
            }
        }
    }

    override fun bindData(data: VMSingleDish?) {
        this.data = data!!
        Glide.with(view).load(data.icon)
                .error(android.R.drawable.ic_menu_close_clear_cancel)
                .placeholder(android.R.drawable.ic_menu_recent_history)
                .into(find(R.id.iv_dish_icon))

        setText(R.id.tv_dish_name, data.name)
        setText(R.id.tv_dish_count, data.count.toString())
        setText(R.id.tv_dish_price, "￥" + Formats.formatAmount(data.price / 100.0))
    }
}