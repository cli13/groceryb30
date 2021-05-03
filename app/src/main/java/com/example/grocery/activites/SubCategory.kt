package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.adapters.MyPageFragmentAdapter
import com.example.grocery.apps.Endpoint
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.Category
import com.example.grocery.models.ProductResponse
import com.example.grocery.models.SubCategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.toolbar.*

class SubCategory : AppCompatActivity() {

    private val TOOLBAR_TITLE = "Sub-Category"
    lateinit var pageAdapter: MyPageFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        pageAdapter = MyPageFragmentAdapter(supportFragmentManager)
        var catID = intent.getIntExtra(Category.KEY_CATEGORY, 1)
        getSubCategory(catID)
        view_pager.adapter = pageAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun getSubCategory(catId: Int) {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoint.getSubCategoryByCatId(catId),
            {
                var gson = Gson()
                var subCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)
                pageAdapter.addFragments(subCategoryResponse.data)
            },
            {
                Log.d("Volley", "${it.message}")
            }
        )
        requestQueue.add(request)
    }
}