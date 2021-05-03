package com.example.grocery.activites


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.adapters.AdapterCategory
import com.example.grocery.apps.Endpoint
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.models.CategoryResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TOOLBAR_TITLE = "Home"
    lateinit var adapterCategory: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()

        getCategory()
        adapterCategory = AdapterCategory(this)
        recycler_view.adapter = adapterCategory
        recycler_view.layoutManager = LinearLayoutManager(this)
        Picasso.get()
            .load("https://www.muruganstoressupermarket.com/wp-content/uploads/2020/02/Easy-Grocery-banner-1.png")
            .centerCrop()
            .resize(400, 200)
            .into(image_view_banner)
    }

    private fun getCategory() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoint.getCategory(),
            {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                adapterCategory.setList(categoryResponse.data)
                progress_bar.visibility = View.GONE
            },
            {
                Log.d("Volley", "${it.message}")
            }
        )
        requestQueue.add(request)
    }
}