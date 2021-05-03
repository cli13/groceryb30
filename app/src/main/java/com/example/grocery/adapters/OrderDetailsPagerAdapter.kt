package com.example.grocery.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.grocery.fragments.OrderDetailsOne
import com.example.grocery.fragments.OrderDetailsTwo
import com.example.grocery.models.OrderData

class OrderDetailsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    final val NUM_ITEMS = 2
    var data: OrderData? = null

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    fun setOrderData(d: OrderData) {
        data = d
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OrderDetailsOne.newInstance(data!!)
            else -> OrderDetailsTwo.newInstance(data!!)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Order Summary"
            else -> "Item (${data?.products?.size})"
        }
    }
}