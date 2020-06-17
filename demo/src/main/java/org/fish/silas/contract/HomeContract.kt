package org.fish.silas.contract

import org.fish.silas.data.entity.ProductEntity
import org.fish.silas.data.entity.PromotionEntity
import org.fish.silas.data.vm.VMCoupon
import org.fish.silas.data.vm.VMPaySuccess
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
        fun showPayResult(success: VMPaySuccess)
        fun showCoupons(coupons: List<VMCoupon>)
    }

    interface IPresenter {
        fun loadProducts()
        fun addShoppingCart(id: String)
        fun removeShoppingCart(id: String)
        fun getDishCount(): Int
        fun loadCoupons()
        fun pay(coupons: List<String>? = null)
        fun destroy()
    }

    interface IModel {
        fun getProducts(listener: OnResultListener<List<VMSingleDish>>)
        fun addShoppingCart(id: String, listener: OnResultListener<Boolean>)
        fun removeShoppingCart(id: String, listener: OnResultListener<Boolean>)
        fun calculateAmount(listener: OnResultListener<Long>)
        fun pay(coupons: List<String>?, listener: OnResultListener<VMPaySuccess>)
        fun resetShoppingCart(listener: OnResultListener<Boolean>)
        fun getCheckedDishCount(): Int
        fun getCoupons(listener: OnResultListener<List<VMCoupon>>)
    }

    interface DishAdapter : Adapter<List<ProductEntity>, List<VMSingleDish>>

    interface CouponAdapter : Adapter<List<PromotionEntity>, List<VMCoupon>>
}