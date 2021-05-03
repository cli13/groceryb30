package com.example.grocery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocery.R
import com.example.grocery.apps.Config.Companion.convertMongoDate
import com.example.grocery.models.OrderData
import kotlinx.android.synthetic.main.fragment_order_details_one.view.*

class OrderDetailsOne : Fragment() {
    private lateinit var data: OrderData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable(OrderData.KEY_ORDER) as OrderData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_order_details_one, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        v.text_view_order_date.text = convertMongoDate(data.date)
        v.text_view_order_status.text = data.payment.paymentStatus
        var shippingAddress =
            "${data.shippingAddress.houseNo} ${data.shippingAddress.streetName}\n${data.shippingAddress.city} ${data.shippingAddress.pincode}"
        v.text_view_order_address.text = shippingAddress
        v.text_view_subtotal.text = "$${data.orderSummary.orderAmount}"
        v.text_view_total_amount.text = "$${data.orderSummary.totalAmount}"
        v.text_view_discount.text = "- $${data.orderSummary.discount}"
        v.text_view_delivery_charges.text = "$${data.orderSummary.deliveryCharges}"
    }

    companion object {
        @JvmStatic
        fun newInstance(data: OrderData) =
            OrderDetailsOne().apply {
                arguments = Bundle().apply {
                    putSerializable(OrderData.KEY_ORDER, data)
                }
            }
    }
}