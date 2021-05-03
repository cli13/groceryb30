package com.example.grocery.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocery.R
import com.example.grocery.adapters.OrderDetailsPagerAdapter
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.models.OrderData
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetails : AppCompatActivity() {
    private val TOOLBAR_TITLE: String = "Order Details"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        var orderDetailsAdapter = OrderDetailsPagerAdapter(supportFragmentManager)
        var data = intent.getSerializableExtra(OrderData.KEY_ORDER) as OrderData
        orderDetailsAdapter.setOrderData(data)
        view_pager.adapter = orderDetailsAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
}