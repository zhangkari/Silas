package org.fish.silas.model;

import org.fish.silas.contract.HomeContract
import org.fish.silas.data.entity.PromotionEntity
import org.fish.silas.data.vm.VMCoupon
import org.fish.silas.utils.Collections
import org.fish.silas.utils.Formats

class CouponAdapterImpl : HomeContract.CouponAdapter {
    override fun adapt(src: List<PromotionEntity>): List<VMCoupon> {
        if (Collections.isEmpty(src)) {
            return listOf()
        }
        val list: MutableList<VMCoupon> = ArrayList()
        for (entity in src) {
            list.add(VMCoupon(entity.id, entity.name, formatCoupon(entity)))
        }
        return list
    }

    private fun formatCoupon(entity: PromotionEntity): String {
        return when {
            entity.type == 0 -> entity.value.toString() + "折"
            entity.type == 1 -> "立减￥" + Formats.formatCurrency(entity.value / 100.0)
            else -> "* invalid *"
        }
    }
}