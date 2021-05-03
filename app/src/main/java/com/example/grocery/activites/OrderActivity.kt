package com.example.grocery.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.adapters.OrderAdapter
import com.example.grocery.apps.Endpoint
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.OrderResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {
    private val TOOLBAR_TITLE: String = "Order"
    lateinit var sm : SessionManager
    lateinit var orderAdapter : OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        orderAdapter = OrderAdapter(this)
        sm = SessionManager(this)

        getOrder()
        recycler_view.adapter = orderAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

    }

    private fun getOrder() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoint.getOrderByUserId(sm.getUserId()!!),
            {
                var gson = Gson()
                var orderResponse = gson.fromJson(it, OrderResponse::class.java)
                orderAdapter.setOrder(orderResponse.data)
            },
            {
                Toast.makeText(applicationContext, "Something went wrong getting order", Toast.LENGTH_SHORT)
            }
        )
        requestQueue.add(request)
    }
}