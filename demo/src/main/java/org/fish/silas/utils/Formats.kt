package org.fish.silas.utils

import java.text.DecimalFormat

object Formats {
    fun formatCurrency(amount: Double): String {
        return "￥" + DecimalFormat("##0.00").format(amount)
    }
}