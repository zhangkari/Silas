package org.fish.silas.data.vm

data class VMPaySuccess(val success: Boolean, val orderAmount: Long, val paidAmount: Long, val coupons: List<String>) : VMBase()