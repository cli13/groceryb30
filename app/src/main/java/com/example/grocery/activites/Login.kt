package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.apps.Endpoint
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class Login : AppCompatActivity() {

    private val ERROR_MESSAGE = "Invalid username or password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        var sessionManager = SessionManager(this)

        button_login.setOnClickListener {
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password", password)

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                Endpoint.getLogin(),
                jsonObject,
                {
                    sessionManager.logUser(it)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                {
                    Toast.makeText(applicationContext, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(jsonRequest)
        }

        text_view_nav_register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }
    }
}