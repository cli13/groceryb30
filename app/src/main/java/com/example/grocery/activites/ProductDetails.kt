package com.example.grocery.activites

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.grocery.R
import com.example.grocery.apps.Endpoint
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.DBHelper
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.CartProduct
import com.example.grocery.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.toolbar.*

class ProductDetails : AppCompatActivity() {
    private val TOOLBAR_TITLE = "Product"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction()
            .add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        var p = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product
        text_view_product_name.text = p.productName
        text_view_product_discount.text = "$${p.price}"
        text_view_product_original.text = "$${p.mrp}"
        text_view_product_description.text = p.description

        text_view_product_original.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        Picasso.get().load(Endpoint.getImage(p.image)).into((image_view_product))

        var db = DBHelper(this)
        var sm = SessionManager(this)
        button_add_to_cart.setOnClickListener {
            if (db.findProductIfExist(p._id)) {
                db.onEditCartQuantity(p._id, CartProduct.MODE_ADD)
                var q = db.getQuantityById(p._id, sm.getUserId()!!)
                Toast.makeText(this, "Amount in cart: $q", Toast.LENGTH_SHORT).show()
            } else {
                db.onAddToCart(p)
            }
        }

    }
}