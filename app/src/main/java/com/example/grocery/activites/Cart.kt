package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.adapters.CartAdapter
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.DBHelper
import kotlinx.android.synthetic.main.activity_cart.*

class Cart : AppCompatActivity() {

    private val TOOLBAR_TITLE = "Cart"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        var cartAdapter = CartAdapter(this)
        recycler_view.adapter = cartAdapter
        initAdapterObs(cartAdapter)
        cartAdapter.updateData()
        recycler_view.layoutManager = LinearLayoutManager(this)

        button_check_out.setOnClickListener {
            var intent = Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initAdapterObs(adapter: CartAdapter) {
        var db = DBHelper(this)
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
                updateOrder()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                if (adapter.itemCount == 0) {
                    text_view_empty_cart.visibility = View.VISIBLE
                    button_check_out.visibility = View.GONE
                    order_summary.visibility = View.GONE
                } else {
                    text_view_empty_cart.visibility = View.GONE
                    button_check_out.visibility = View.VISIBLE
                    order_summary.visibility = View.VISIBLE
                }
            }

            fun updateOrder() {
                var res = db.getOrderSummary()
                text_view_original_price.text = "$${res[0]}"
                text_view_discount_price.text = "$${res[1]}"
            }
        })
    }
}