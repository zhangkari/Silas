package org.fish.silas.utils

object Counters {
    private var count = 0

    fun increase() {
        count++
    }

    fun decrease() {
        count--
    }

    fun getCount(): Int {
        return count
    }
}