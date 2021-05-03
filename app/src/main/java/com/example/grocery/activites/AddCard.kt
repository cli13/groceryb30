package com.example.grocery.activites

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.grocery.R
import com.example.grocery.fragments.ToolBarFragment
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCard : AppCompatActivity() {
    private val TOOLBAR_TITLE: String = "Payment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction()
            .add(R.id.toolbar, ToolBarFragment.newInstance(TOOLBAR_TITLE)).commit()

        button_save_card.setOnClickListener {
            var cardNum = edit_text_card_number.text.toString()
            var returnIntent = Intent()
            returnIntent.putExtra("Card", cardNum)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}