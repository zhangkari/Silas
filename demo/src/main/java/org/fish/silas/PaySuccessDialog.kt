package org.fish.silas

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.fish.silas.data.vm.VMPaySuccess
import org.fish.silas.utils.Formats
import org.fish.silas.viewbinder.PaySuccessVBinder
import org.karic.smartadapter.SmartAdapter

class PaySuccessDialog : ZygoteDialog() {

    companion object {
        private const val tag = "PaySuccessTag"

        private fun newInstance(result: VMPaySuccess): PaySuccessDialog {
            val dialog = PaySuccessDialog()
            val bundle = Bundle()
            bundle.putSerializable("result", result)
            dialog.arguments = bundle
            return dialog
        }

        fun show(fragMgr: FragmentManager, result: VMPaySuccess) {
            newInstance(result).show(fragMgr, tag)
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.dialog_pay_success
    }

    override fun getMenuLayout(): Int {
        return 0
    }

    override fun resetStyle() {
        super.resetStyle()

        val dialog = dialog ?: return
        val window = dialog.window ?: return

        val params = window.attributes
        params.width = (resources.displayMetrics.widthPixels * 0.8f).toInt()
        params.gravity = Gravity.CENTER
        window.attributes = params
        window.setDimAmount(0.5f)
    }

    override fun init() {
        val result: VMPaySuccess = arguments!!.getSerializable("result") as VMPaySuccess

        rootView.findViewById<TextView>(R.id.tv_order_amount).text = Formats.formatCurrency(result.orderAmount / 100.0)
        rootView.findViewById<TextView>(R.id.tv_paid_amount).text = Formats.formatCurrency(result.paidAmount / 100.0)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.coupons)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = SmartAdapter()
        adapter.register(String::class.java, PaySuccessVBinder())
        adapter.setData(result.coupons)
        recyclerView.adapter = adapter
    }
}