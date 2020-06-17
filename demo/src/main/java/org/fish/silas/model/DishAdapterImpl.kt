package org.fish.silas.model

import org.fish.silas.contract.HomeContract
import org.fish.silas.data.entity.ProductEntity
import org.fish.silas.data.vm.VMSingleDish

class DishAdapterImpl : HomeContract.DishAdapter {
    override fun adapt(src: List<ProductEntity>): List<VMSingleDish> {
        if (src.isEmpty()) {
            return ArrayList()
        }
        val list = ArrayList<VMSingleDish>(src.size)
        for (entity in src) {
            list.add(VMSingleDish(entity.id, entity.name, entity.price, entity.iconUrl))
        }
        return list
    }
}