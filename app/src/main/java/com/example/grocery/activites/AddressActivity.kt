package com.example.grocery.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.adapters.AddressAdapter
import com.example.grocery.fragments.ToolBarFragment
import com.example.grocery.models.Address
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.recycler_view
import kotlinx.android.synthetic.main.activity_cart.*

class AddressActivity : AppCompatActivity() {
    private val TOOLBAR_TITLE = "Select an address"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction()
            .add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()

        if(intent.getBooleanExtra("MODE_EDIT",false)){
            button_place_order.visibility = View.GONE
        }

        var adapterAddress = AddressAdapter(this)
        initAdapterObs(adapterAddress)
        recycler_view.adapter = adapterAddress
        adapterAddress.getAddresses()
        recycler_view.layoutManager = LinearLayoutManager(this)
        //button listeners
        button_add_address.setOnClickListener {
            var intent = Intent(this, AddAddress::class.java)
            startActivityForResult(intent, 1)
        }
        button_place_order.setOnClickListener {
            var intent = Intent(this, Payment::class.java)
            intent.putExtra(Address.KEY_ADDRESS, adapterAddress.getSelectedPosition())
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        finish()
        startActivity(intent)
    }

    private fun initAdapterObs(adapter: AddressAdapter) {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                if (adapter.itemCount == 0) {
                    button_place_order.visibility = View.GONE
                    text_view_empty_address.visibility = View.VISIBLE
                } else {
                    text_view_empty_address.visibility = View.GONE
                    button_place_order.visibility = View.VISIBLE
                }
            }
        })
    }
}