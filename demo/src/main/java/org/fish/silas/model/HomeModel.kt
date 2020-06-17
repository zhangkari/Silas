package org.fish.silas.model

import android.os.Handler
import android.os.Looper
import org.fish.silas.contract.HomeContract
import org.fish.silas.data.entity.ProductEntity
import org.fish.silas.data.entity.PromotionEntity
import org.fish.silas.data.vm.VMCoupon
import org.fish.silas.data.vm.VMPaySuccess
import org.fish.silas.data.vm.VMSingleDish
import org.fish.silas.utils.Collections
import org.fish.silas.utils.Formats
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class HomeModel(private val dishAdapter: HomeContract.DishAdapter, private val couponAdapter: HomeContract.CouponAdapter) : HomeContract.IModel {

    private val dish: List<ProductEntity> = listOf(
            ProductEntity("1011", null, "回锅肉", 1650, "http://image1.qianqianhua.com/uploads/20180717/11/1531798229-HPlZKeCVsx.jpg"),
            ProductEntity("1012", null, "水煮肉片", 2500, "http://n.sinaimg.cn/sinacn19/280/w1740h940/20180718/4a58-hfnsvza2815326.jpg"),
            ProductEntity("1013", null, "西湖醋鱼", 4550, "http://5b0988e595225.cdn.sohucs.com/images/20180102/d9464cbc75ed40aa82f0758f8cdca9de.jpeg")
    )

    private val coupons: List<PromotionEntity> = listOf(
            PromotionEntity("2011", "折扣券", 0, 95),
            PromotionEntity("2012", "折后立减券", 1, 250)
    )

    private val handler = Handler(Looper.getMainLooper())

    private val checked: MutableList<ProductEntity> = LinkedList()

    override fun getProducts(listener: OnResultListener<List<VMSingleDish>>) {
        loadProducts(object : OnResultListener<List<ProductEntity>> {
            override fun onSuccess(t: List<ProductEntity>) {
                handler.post {
                    listener.onSuccess(dishAdapter.adapt(t))
                }
            }

            override fun onError(code: Int, message: String) {
                handler.post {
                    listener.onError(code, message)
                }
            }
        })
    }

    private fun loadProducts(listener: OnResultListener<List<ProductEntity>>) {
        thread(start = true, isDaemon = false, contextClassLoader = null, name = "LoadProductsThread", priority = -1) {
            val list = ArrayList(dish)
            Thread.sleep(1000)
            listener.onSuccess(list)
        }
    }

    override fun removeShoppingCart(id: String, listener: OnResultListener<Boolean>) {
        val dish = findDishById(id, checked)
        if (dish == null) {
            listener.onSuccess(false)
        } else {
            checked.remove(dish)
            listener.onSuccess(true)
        }
    }

    override fun addShoppingCart(id: String, listener: OnResultListener<Boolean>) {
        val dish = findDishById(id, dish)
        if (dish == null) {
            listener.onSuccess(false)
        } else {
            checked.add(dish)
            listener.onSuccess(true)
        }
    }

    override fun calculateAmount(listener: OnResultListener<Long>) {
        calculateShoppingCart(object : OnResultListener<Long> {
            override fun onSuccess(t: Long) {
                handler.post {
                    listener.onSuccess(t)
                }
            }

            override fun onError(code: Int, message: String) {
                handler.post {
                    listener.onError(code, message)
                }
            }
        })
    }

    private fun calculateShoppingCart(listener: OnResultListener<Long>) {
        thread(start = true, isDaemon = false, contextClassLoader = null, name = "CalculateShoppingCartThread", priority = -1) {

            var total = 0L
            for (item in checked) {
                total += item.price
            }

            Thread.sleep(300)
            listener.onSuccess(total)
        }
    }

    private fun calculate(coupons: List<String>?, listener: OnResultListener<VMPaySuccess>) {
        thread(start = true, isDaemon = false, contextClassLoader = null, name = "CalculateThread", priority = -1) {

            var total = 0L
            for (item in checked) {
                total += item.price
            }
            val orderAmount = total

            if (coupons != null && !Collections.isEmpty(coupons)) {
                coupons.sortedWith(Comparator { o1, o2 -> o1.compareTo(o2) })
                for (id in coupons) {
                    total = calculateWithCouponId(total, id)
                }
            }

            Thread.sleep(1000)
            listener.onSuccess(VMPaySuccess(true, orderAmount, total, formatCoupons(coupons)))
        }
    }

    private fun calculateWithCouponId(total: Long, id: String): Long {
        val coupon = findCouponById(id)
        return if (coupon == null) {
            total
        } else {
            when {
                coupon.type == 0 -> total * coupon.value / 100
                coupon.type == 1 -> total - coupon.value
                else -> total
            }
        }
    }

    private fun findCouponById(id: String): PromotionEntity? {
        for (c in coupons) {
            if (c.id == id) {
                return c
            }
        }
        return null
    }

    private fun findDishById(id: String, list: List<ProductEntity>): ProductEntity? {
        for (d in list) {
            if (id == d.id) {
                return d
            }
        }
        return null
    }

    override fun pay(coupons: List<String>?, listener: OnResultListener<VMPaySuccess>) {
        payReal(coupons, object : OnResultListener<VMPaySuccess> {
            override fun onSuccess(t: VMPaySuccess) {
                handler.post {
                    listener.onSuccess(t)
                }
            }

            override fun onError(code: Int, message: String) {
                handler.post {
                    listener.onError(code, message)
                }
            }
        })
    }

    private fun formatCoupons(coupons: List<String>?): List<String> {
        if (coupons == null || Collections.isEmpty(coupons)) {
            return ArrayList(0)
        }

        val list = ArrayList<String>()
        for (id in coupons) {
            val c = findCouponById(id)
            if (c != null) {
                val name = when {
                    c.type == 0 -> c.name + c.value.toString() + "折"
                    c.type == 1 -> c.name + Formats.formatCurrency(c.value / 100.0)
                    else -> "**无效券**"
                }
                list.add(name)
            }
        }
        return list
    }

    private fun payReal(coupons: List<String>?, listener: OnResultListener<VMPaySuccess>) {
        thread(true, isDaemon = false, contextClassLoader = null, name = "PayThread", priority = -1) {
            Thread.sleep(1000)
            calculate(coupons, object : OnResultListener<VMPaySuccess> {
                override fun onSuccess(t: VMPaySuccess) {
                    listener.onSuccess(t)
                }

                override fun onError(code: Int, message: String) {
                    listener.onError(code, message)
                }
            })
        }
    }

    override fun resetShoppingCart(listener: OnResultListener<Boolean>) {
        checked.clear()
        listener.onSuccess(true)
    }

    override fun getCheckedDishCount(): Int {
        return checked.size
    }

    override fun getCoupons(listener: OnResultListener<List<VMCoupon>>) {
        loadCoupons(object : OnResultListener<List<PromotionEntity>> {
            override fun onSuccess(t: List<PromotionEntity>) {
                handler.post {
                    listener.onSuccess(couponAdapter.adapt(t))
                }
            }

            override fun onError(code: Int, message: String) {
                handler.post {
                    listener.onError(code, message)
                }
            }
        })
    }

    private fun loadCoupons(listener: OnResultListener<List<PromotionEntity>>) {
        thread(start = true, isDaemon = false, contextClassLoader = null, name = "LoadPromotionsThread", priority = -1) {
            val list = ArrayList(coupons)
            Thread.sleep(1000)
            listener.onSuccess(list)
        }
    }
}