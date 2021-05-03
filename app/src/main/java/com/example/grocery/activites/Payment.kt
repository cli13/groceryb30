package com.example.grocery.activites

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.apps.Config
import com.example.grocery.apps.Endpoint
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.DBHelper
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.Address
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_order_placed.view.*
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONArray
import org.json.JSONObject

class Payment : AppCompatActivity() {
    private val TOOLBAR_TITLE: String = "Payment"
    private var payMode = "Cash"

    private val LAUNCH_CARD_ACTIVITY = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction()
            .add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        var db = DBHelper(this)
        var sm = SessionManager(this)

        var userObject = JSONObject()
        userObject.put("_id", sm.getUserId())
        userObject.put("email", sm.getEmail())
        userObject.put("mobile", sm.getMobile())

        var address = intent.getSerializableExtra(Address.KEY_ADDRESS) as Address
        var shippingAddressObject = JSONObject()
        shippingAddressObject.put("_id", address._id)
        shippingAddressObject.put("city", address.city)
        shippingAddressObject.put("houseNo", address.houseNo)
        shippingAddressObject.put("pincode", address.pincode)
        shippingAddressObject.put("streetName", address.streetName)
        shippingAddressObject.put("type", address.type)

        var products = db.getAllFromCart()
        var productObject = JSONArray()
        for(product in products){
            var obj = JSONObject()
            obj.put("image", product.image)
            obj.put("mrp", product.mrp)
            obj.put("productName", product.name)
            obj.put("quantity", product.quantity)
            obj.put("price", product.price)
            productObject.put(obj)
        }

        var summary = db.getOrderSummary()
        var deliveryCharges = if (summary[1] >= Config.IS_FREE_DELIVERY) 0 else Config.DELIVERY_COST
        var orderAmount = summary[0]
        var discount = summary[0] - summary[1]
        var ourPrice = summary[1]
        var totalAmount = ourPrice + deliveryCharges
        var orderSummaryObject = JSONObject()
        orderSummaryObject.put("deliveryCharges", deliveryCharges)
        orderSummaryObject.put("discount", discount)
        orderSummaryObject.put("orderAmount", orderAmount)
        orderSummaryObject.put("ourPrice", ourPrice)
        orderSummaryObject.put("totalAmount", totalAmount)

        var postObject = JSONObject()
        postObject.put("userId", sm.getUserId())
        postObject.put("user", userObject)
        postObject.put("orderSummary", orderSummaryObject)
        postObject.put("products", productObject)
        postObject.put("shippingAddress", shippingAddressObject)

        var paymentObject = JSONObject()
        paymentObject.put("PaymentMode", payMode)
        paymentObject.put("paymentStatus", "Pending")
        postObject.put("payment", paymentObject)

        button_pay.setOnClickListener {
            sendOrder(postObject, db)
            finishAffinity()
            startActivity(Intent(this, OrderPlaced::class.java))
        }

        button_add_card.setOnClickListener {
            startActivityForResult(Intent(this, AddCard::class.java), LAUNCH_CARD_ACTIVITY)
        }

        //set dynamic text
        text_view_total_amount.text = "$${totalAmount}"
        text_view_subtotal.text = "$${orderAmount}"
        text_view_delivery_charges.text = "$${deliveryCharges}"
        text_view_total_amount_bottom.text = "$${totalAmount}"
        text_view_discount.text = "-$${discount}"
        text_view_amount_saved.text = "You will save $${discount} dollar this order!"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_CARD_ACTIVITY){
            payMode = "Online"
            card_stuff.visibility = View.VISIBLE
            radio_card.isChecked = true
            radio_cash.isChecked = false
            text_view_card_details.text = data?.getStringExtra("Card")
        }
    }

    private fun sendOrder(postObject : JSONObject, db : DBHelper){
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoint.getOrder(),
            postObject,
            {
                db.deleteAllCartItems()
            },
            {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
            }
        )
        requestQueue.add(request)
    }

    fun onRadioButtonClicked(v : View){
        if (v is RadioButton) {
            val checked = v.isChecked
        }
        when (v) {
            radio_cash -> {
                payMode = "Cash"
                card_stuff.visibility = View.GONE
            }
            radio_card -> {
                payMode = "Online"
                card_stuff.visibility = View.VISIBLE
            }
        }
    }
}