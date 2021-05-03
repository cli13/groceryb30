package com.example.grocery.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.apps.Endpoint
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        button_register.setOnClickListener {
            var firstName = edit_text_first_name.text.toString()
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()
            var phone = edit_text_phone.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("firstName", firstName)
            jsonObject.put("email", email)
            jsonObject.put("password", password)
            jsonObject.put("mobile", phone)

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                Endpoint.getRegister(),
                jsonObject,
                {
                    startActivity(Intent(this, Login::class.java))
                },
                {
                    val responseBody = String(it.networkResponse.data, Charset.forName("UTF-8"))
                    val data = JSONObject(responseBody)
                    val msg = data.get("message")
                    Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_LONG).show()
                }
            )
            requestQueue.add(jsonRequest)
        }

        text_view_nav_login.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}