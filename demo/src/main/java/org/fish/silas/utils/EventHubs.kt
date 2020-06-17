package org.fish.silas.utils

import org.fish.silas.data.event.ADD_CLICK
import org.fish.silas.data.event.CouponCheckedEvent
import org.fish.silas.data.event.DishClickEvent
import org.fish.silas.data.event.SUBTRACT_CLICK
import org.greenrobot.eventbus.EventBus

object EventHubs {
    fun registerClickReceiver(obj: Any) {
        EventBus.getDefault().register(obj)
    }

    fun unregisterClickReceiver(obj: Any) {
        EventBus.getDefault().unregister(obj)
    }

    fun postAddDishEvent(id: String) {
        EventBus.getDefault().post(DishClickEvent(ADD_CLICK, id))
    }

    fun postSubtractDishEvent(id: String) {
        EventBus.getDefault().post(DishClickEvent(SUBTRACT_CLICK, id))
    }

    fun postCheckCouponEvent(list: List<String>) {
        EventBus.getDefault().post(CouponCheckedEvent(list))
    }
}