package org.fish.silas.utils

import java.text.DecimalFormat

object Formats {
    fun formatAmount(amount: Double): String {
        return DecimalFormat("##0.00").format(amount)
    }
}