package org.fish.silas.viewbinder

import org.fish.silas.R
import org.karic.smartadapter.ViewBinder

class PaySuccessVBinder : ViewBinder<String>(R.layout.vb_pay_success) {
    override fun bindData(data: String?) {
        setText(R.id.tv_item, data)
    }
}