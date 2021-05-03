package com.example.grocery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery.R
import com.example.grocery.adapters.OrderDetailProductAdapter
import com.example.grocery.models.OrderData
import kotlinx.android.synthetic.main.fragment_order_details_two.view.*


class OrderDetailsTwo : Fragment() {
    private var data: OrderData? = null

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
        var v = inflater.inflate(R.layout.fragment_order_details_two, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        var adapterProduct = OrderDetailProductAdapter(activity!!)
        adapterProduct.setList(ArrayList(data?.products))
        v.recycler_view.adapter = adapterProduct
        v.recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        @JvmStatic
        fun newInstance(d: OrderData) =
            OrderDetailsTwo().apply {
                arguments = Bundle().apply {
                    putSerializable(OrderData.KEY_ORDER, d)
                }
            }
    }
}