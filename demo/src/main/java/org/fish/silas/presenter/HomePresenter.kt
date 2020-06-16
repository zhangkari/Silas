package org.fish.silas.presenter

import org.fish.silas.contract.HomeContract
import org.fish.silas.data.vm.VMSingleDish
import org.fish.silas.model.OnResultListener

class HomePresenter(private var view: HomeContract.IView?, private val model: HomeContract.IModel) : HomeContract.IPresenter {

    override fun loadProducts() {
        view?.showLoading()
        model.getProducts(object : OnResultListener<List<VMSingleDish>> {
            override fun onSuccess(t: List<VMSingleDish>) {
                view?.dismissLoading()
                view?.showProducts(t)
            }

            override fun onError(code: Int, message: String) {
                view?.dismissLoading()
                view?.showError(code, message)
            }
        })
    }

    override fun addShoppingCart(id: String) {
        view?.showLoading()
        model.addShoppingCart(id, object : OnResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
                if (!t) {
                    view?.dismissLoading()
                    view?.showError(500, "添加到购物车失败")
                    return
                }

                model.calculateAmount(object : OnResultListener<Long> {
                    override fun onSuccess(t: Long) {
                        view?.dismissLoading()
                        view?.showPayAmount(t)
                    }

                    override fun onError(code: Int, message: String) {
                        view?.dismissLoading()
                        view?.showError(code, message)
                    }
                })
            }

            override fun onError(code: Int, message: String) {
                view?.dismissLoading()
                view?.showError(code, message)
            }
        })
    }

    override fun removeShoppingCart(id: String) {
        view?.showLoading()
        model.removeShoppingCart(id, object : OnResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
                if (!t) {
                    view?.dismissLoading()
                    view?.showError(500, "移除购物车失败")
                    return
                }

                model.calculateAmount(object : OnResultListener<Long> {
                    override fun onSuccess(t: Long) {
                        view?.dismissLoading()
                        view?.showPayAmount(t)
                    }

                    override fun onError(code: Int, message: String) {
                        view?.dismissLoading()
                        view?.showError(code, message)
                    }
                })
            }

            override fun onError(code: Int, message: String) {
                view?.dismissLoading()
                view?.showError(code, message)
            }
        })
    }

    override fun pay() {
        view?.showLoading()
        model.pay(object : OnResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
                view?.dismissLoading()
                view?.showPayResult(t)

                model.resetShoppingCart(object : OnResultListener<Boolean> {
                    override fun onSuccess(t: Boolean) {
                        view?.dismissLoading()
                        if (t) {
                            loadProducts()
                        } else {
                            view?.showError(100, "清空购物车失败")
                        }
                    }

                    override fun onError(code: Int, message: String) {
                        view?.dismissLoading()
                        view?.showError(code, message)
                    }
                })
            }

            override fun onError(code: Int, message: String) {
                view?.dismissLoading()
                view?.showError(code, message)
            }
        })
    }

    override fun destroy() {
        view = null
    }

    override fun getDishCount(): Int {
        return model.getCheckedDishCount()
    }
}