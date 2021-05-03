package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocery.R
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.Address
import kotlinx.android.synthetic.main.activity_my_account.*

class MyAccount : AppCompatActivity() {
    private val TOOLBAR_TITLE: String = "My Account"
    private lateinit var sm : SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()
        sm = SessionManager(this)

        text_view_order.setOnClickListener { startActivity(Intent(this, OrderActivity::class.java)) }
        text_view_manage_address.setOnClickListener { startActivity(Intent(this, AddressActivity::class.java).putExtra("MODE_EDIT", true)) }

        text_view_user_name.text = sm.getName()
        text_view_user_phone.text = sm.getMobile()
    }
}