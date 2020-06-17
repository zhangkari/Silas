package org.fish.silas.utils

import org.junit.Assert
import org.junit.Test

class FormatsTest {
    @Test
    fun formatAmountTest() {
        var text = Formats.formatCurrency(0 / 100.0)
        Assert.assertEquals("￥0.00", text)


        text = Formats.formatCurrency(150 / 100.0)
        Assert.assertEquals("￥1.50", text)
    }
}