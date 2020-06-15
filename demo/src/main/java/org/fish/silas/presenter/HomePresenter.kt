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

    override fun addProduct(id: String) {
        view?.showLoading()
        model.addDish(id, object : OnResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
                if (!t) {
                    view?.dismissLoading()
                    view?.showError(500, "Add dish failed")
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

    override fun removeProduct(id: String) {
        view?.showLoading()
        model.subtract(id, object : OnResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
                if (!t) {
                    view?.dismissLoading()
                    view?.showError(500, "Subtract dish failed")
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

                model.reset(object : OnResultListener<Void> {
                    override fun onSuccess(t: Void) {
                        view?.dismissLoading()
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
}