package org.fish.silas.model

interface OnResultListener<T> {
    fun onSuccess(t: T)
    fun onError(code: Int, message: String)
}