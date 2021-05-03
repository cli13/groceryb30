package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.grocery.R
import com.example.grocery.managers.SessionManager
import kotlinx.android.synthetic.main.activity_initial.*

class InitialActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        init()
    }

    private fun init() {
        var sessionManager = SessionManager(this)
        if(sessionManager.isLoggedIn()){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        button_login.setOnClickListener(this)
        button_register.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v){
            button_login -> {
                var intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            button_register -> {
                var intent = Intent(this, Register::class.java)
                startActivity(intent)
            }
        }
    }
}