package com.example.grocery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.adapters.AdapterProduct
import com.example.grocery.apps.Endpoint
import com.example.grocery.models.ProductResponse
import com.example.grocery.models.SubCategory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_category.view.*

class SubCategoryFragment : Fragment() {

    private var subCatgeory : SubCategory? = null
    lateinit var productAdapter : AdapterProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCatgeory = it.getSerializable(SubCategory.KEY_SUBCATEGORY) as SubCategory
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_category, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        getProducts()
        productAdapter = AdapterProduct(requireActivity())
        v.recycler_view.adapter = productAdapter
        v.recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    private fun getProducts() {
        var requestQueue = Volley.newRequestQueue(activity)
        var request = StringRequest(
            Request.Method.GET,
            Endpoint.getProductBySubId(subCatgeory!!.subId),
            {
                var gson = Gson()
                var product = gson.fromJson(it, ProductResponse::class.java)
                productAdapter.setList(product.data)
            },
            {
                Log.d("Volley", "${it.message}")
            }
        )
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(sub : SubCategory) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SubCategory.KEY_SUBCATEGORY, sub)
                }
            }
    }
}