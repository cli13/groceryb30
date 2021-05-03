package com.example.grocery.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.grocery.R
import com.example.grocery.activites.Cart
import com.example.grocery.activites.InitialActivity
import com.example.grocery.activites.MyAccount
import com.example.grocery.managers.DBHelper
import com.example.grocery.managers.SessionManager
import kotlinx.android.synthetic.main.cart_icon.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*


private const val TITLE = "title"

class ToolBarFragment : Fragment() {
    private var textViewCartNumber: TextView? = null
    private var title2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            title2 = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.toolbar, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        setUpToolBar(v.toolbar)
        v.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        v.toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartCount(textViewCartNumber)
    }

    private fun setUpToolBar(toolbar: Toolbar) {
        toolbar.title = title2
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        var item = menu?.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.cart_icon)
        var view = MenuItemCompat.getActionView(item)
        textViewCartNumber = view.text_view_cart_count
        updateCartCount(view)
        view.setOnClickListener {
            startActivity(Intent(activity, Cart::class.java))
        }
    }

    private fun updateCartCount(v: View?) {
        var db = DBHelper(activity!!)
        var count = db.getCartNumber()
        if (count == 0) {
            v?.text_view_cart_count?.visibility = View.GONE
        } else {
            v?.text_view_cart_count?.visibility = View.VISIBLE
            v?.text_view_cart_count?.text = count.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_account -> startActivity(Intent(activity, MyAccount::class.java))
            R.id.menu_logout -> {
                var sessionManager = SessionManager(activity!!)
                sessionManager.logout()
                requireActivity().finishAffinity()
                startActivity(Intent(requireContext(), InitialActivity::class.java))
            }
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String) =
            ToolBarFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                }
            }
    }
}