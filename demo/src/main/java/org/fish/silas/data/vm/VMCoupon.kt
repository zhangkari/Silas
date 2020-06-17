package org.fish.silas.data.vm

data class VMCoupon(val id: String, val type: String, val name: String, var checked: Boolean = false) : VMBase()