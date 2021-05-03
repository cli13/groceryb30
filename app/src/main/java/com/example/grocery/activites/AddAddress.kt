package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.apps.Endpoint
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.Address
import kotlinx.android.synthetic.main.activity_add_address.*
import org.json.JSONObject

class AddAddress : AppCompatActivity() {
    private val TOOLBAR_TITLE: String = "Delivery Address"

    private var type: String = "Home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        init()
    }

    private fun init() {
        val sm = SessionManager(this)
        supportFragmentManager.beginTransaction()
            .add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()

        var address = intent.getSerializableExtra("ADDRESS") as Address?
        var edit = intent.getBooleanExtra("EDIT_MODE", false)
        checkEdit(edit, address)


        //buttons
        button_edit_address.setOnClickListener {
            var jsonBody = JSONObject()
            jsonBody.put("pincode", edit_text_pincode.text)
            jsonBody.put("city", edit_text_city.text)
            jsonBody.put("streetName", edit_text_street.text)
            jsonBody.put("houseNo", edit_text_house_number.text)
            jsonBody.put("type", type)
            jsonBody.put("userId", sm.getUserId())

            var requestQueue = Volley.newRequestQueue(this)

            var request = JsonObjectRequest(
                Request.Method.PUT,
                Endpoint.getAddressById(address!!._id),
                jsonBody,
                {
                    Toast.makeText(
                        applicationContext,
                        "Successfully editted the address",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                {
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong address was not saved",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            requestQueue.add(request)
        }
        button_add_address.setOnClickListener {
            var jsonBody = JSONObject()
            jsonBody.put("pincode", edit_text_pincode.text)
            jsonBody.put("city", edit_text_city.text)
            jsonBody.put("streetName", edit_text_street.text)
            jsonBody.put("houseNo", edit_text_house_number.text)
            jsonBody.put("type", type)
            jsonBody.put("userId", sm.getUserId())

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoint.postAddress(),
                jsonBody,
                {
                    Toast.makeText(
                        applicationContext,
                        "Successfully added the address",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                {
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong address was not saved",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            requestQueue.add(request)
        }
        //end of buttons
    }

    private fun checkEdit(edit: Boolean, address: Address?) {
        if (edit) {
            button_add_address.visibility = View.GONE
            button_edit_address.visibility = View.VISIBLE

            edit_text_city.text = Editable.Factory.getInstance().newEditable(address?.city)
            edit_text_pincode.text =
                Editable.Factory.getInstance().newEditable(address?.pincode.toString())
            edit_text_house_number.text =
                Editable.Factory.getInstance().newEditable(address?.houseNo)
            edit_text_street.text = Editable.Factory.getInstance().newEditable(address?.streetName)
        }
    }

    fun onRadioButtonClicked(v: View) {
        if (v is RadioButton) {
            val checked = v.isChecked
        }
        when (v) {
            radio_home -> {
                type = "Home"
            }
            radio_office -> {
                type = "Office"
            }
            radio_other -> {
                type = "Other"
            }
        }
    }
}