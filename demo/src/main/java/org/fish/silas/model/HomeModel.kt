package org.fish.silas.model

import android.os.Handler
import android.os.Looper
import org.fish.silas.contract.HomeContract
import org.fish.silas.data.entity.ProductEntity
import org.fish.silas.data.vm.VMSingleDish
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class HomeModel(private val adapter: HomeContract.HomeAdapter) : HomeContract.IModel {
    private val dish: List<ProductEntity> = listOf(
            ProductEntity("1011", null, "回锅肉", 1650, "http://image1.qianqianhua.com/uploads/20180717/11/1531798229-HPlZKeCVsx.jpg"),
            ProductEntity("2011", null, "水煮肉片", 2500, "http://n.sinaimg.cn/sinacn19/280/w1740h940/20180718/4a58-hfnsvza2815326.jpg"),
            ProductEntity("3011", null, "西湖醋鱼", 4550, "http://5b0988e595225.cdn.sohucs.com/images/20180102/d9464cbc75ed40aa82f0758f8cdca9de.jpeg")
    )

    private val handler = Handler(Looper.getMainLooper())

    private val checked: MutableList<ProductEntity> = LinkedList()

    override fun getProducts(listener: OnResultListener<List<VMSingleDish>>) {
        loadProducts(object : OnResultListener<List<ProductEntity>> {
            override fun onSuccess(t: List<ProductEntity>) {
                handler.post {
                    listener.onSuccess(adapter.adapt(t))
                }
            }

            override fun onError(code: Int, message: String) {
                handler.post {
                    listener.onError(code, message)
                }
            }
        })
    }

    override fun getComboProducts(id: String, listener: OnResultListener<ProductEntity>) {

    }

    private fun loadProducts(listener: OnResultListener<List<ProductEntity>>) {
        thread(start = true, isDaemon = false, contextClassLoader = null, name = "LoadProductsThread", priority = -1) {
            val list = ArrayList(dish)
            Thread.sleep(2000)
            listener.onSuccess(list)
        }
    }

    override fun subtract(id: String, listener: OnResultListener<Boolean>) {
        val dish = findDishById(id, checked)
        if (dish == null) {
            listener.onSuccess(false)
        } else {
            checked.remove(dish)
            listener.onSuccess(true)
        }
    }

    override fun addDish(id: String, listener: OnResultListener<Boolean>) {
        val dish = findDishById(id, dish)
        if (dish == null) {
            listener.onSuccess(false)
        } else {
            checked.add(dish)
            listener.onSuccess(true)
        }
    }

    override fun calculateAmount(listener: OnResultListener<Long>) {
        calculate(object : OnResultListener<Long> {
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

    private fun calculate(listener: OnResultListener<Long>) {
        thread(start = true, isDaemon = false, contextClassLoader = null, name = "CalculateThread", priority = -1) {
            var total = 0L
            for (item in checked) {
                total += item.price
            }
            Thread.sleep(2000)
            listener.onSuccess(total)
        }
    }

    private fun findDishById(id: String, list: List<ProductEntity>): ProductEntity? {
        for (d in list) {
            if (id == d.id) {
                return d
            }
        }
        return null
    }

    override fun pay(listener: OnResultListener<Boolean>) {
        payReal(object : OnResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
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

    private fun payReal(listener: OnResultListener<Boolean>) {
        thread(true, isDaemon = false, contextClassLoader = null, name = "PayThread", priority = -1) {
            Thread.sleep(2000)
            calculate(object : OnResultListener<Long> {
                override fun onSuccess(t: Long) {
                    listener.onSuccess(true)
                }

                override fun onError(code: Int, message: String) {
                    listener.onError(code, message)
                }
            })
        }
    }

    override fun reset(listener: OnResultListener<Void>) {
        checked.clear()
    }
}