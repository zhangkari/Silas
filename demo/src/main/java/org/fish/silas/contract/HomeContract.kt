package org.fish.silas.contract

import org.fish.silas.data.entity.ProductEntity
import org.fish.silas.data.vm.VMSingleDish
import org.fish.silas.model.Adapter
import org.fish.silas.model.OnResultListener

interface HomeContract {
    interface IView {
        fun showLoading()
        fun dismissLoading()
        fun showProducts(data: List<VMSingleDish>)
        fun showError(code: Int, message: String)
        fun showPayAmount(amount: Long)
        fun showPayResult(success: Boolean)
    }

    interface IPresenter {
        fun loadProducts()
        fun addShoppingCart(id: String)
        fun removeShoppingCart(id: String)
        fun getDishCount(): Int
        fun pay()
        fun destroy()
    }

    interface IModel {
        fun getProducts(listener: OnResultListener<List<VMSingleDish>>)
        fun getComboProducts(id: String, listener: OnResultListener<ProductEntity>)
        fun addShoppingCart(id: String, listener: OnResultListener<Boolean>)
        fun removeShoppingCart(id: String, listener: OnResultListener<Boolean>)
        fun calculateAmount(listener: OnResultListener<Long>)
        fun pay(listener: OnResultListener<Boolean>)
        fun resetShoppingCart(listener: OnResultListener<Boolean>)
        fun getCheckedDishCount(): Int
    }

    interface HomeAdapter : Adapter<List<ProductEntity>, List<VMSingleDish>>
}