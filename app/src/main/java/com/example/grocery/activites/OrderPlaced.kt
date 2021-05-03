package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocery.R
import kotlinx.android.synthetic.main.activity_order_placed.*

class OrderPlaced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)
        init()
    }

    private fun init() {
        button_home.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}