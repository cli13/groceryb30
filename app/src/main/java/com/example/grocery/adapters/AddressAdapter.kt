package com.example.grocery.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocery.R
import com.example.grocery.activites.AddAddress
import com.example.grocery.apps.Endpoint
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.Address
import com.example.grocery.models.AddressResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.row_layout_address.view.*

class AddressAdapter(var mContext: Context) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var addressList = ArrayList<Address>()
    private var sm = SessionManager(mContext)
    private var lastCheckedPosition = 0;

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(a: Address) {
            itemView.text_view_city.text = a.city
            itemView.text_view_house_no.text = a.houseNo
            itemView.text_view_pincode.text = a.pincode.toString()
            itemView.text_view_street_number.text = a.streetName
            itemView.text_view_type.text = a.type

            itemView.image_view_cancel.setOnClickListener {
                deleteAddress(a)
            }

            itemView.image_view_edit.setOnClickListener {
                var intent = Intent(mContext, AddAddress::class.java)
                intent.putExtra("EDIT_MODE", true)
                intent.putExtra("ADDRESS", a)
                (mContext as Activity).startActivityForResult(intent, 1)
            }

            itemView.radio_button_address.isChecked = adapterPosition == lastCheckedPosition

            itemView.radio_button_address.setOnCheckedChangeListener { buttonView, isChecked ->
                lastCheckedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    fun deleteAddress(a: Address) {
        var requestQueue = Volley.newRequestQueue(mContext)
        var request = StringRequest(
            Request.Method.DELETE,
            Endpoint.getAddressById(a._id),
            {
                getAddresses()
            },
            {
                Toast.makeText(mContext, "Error something went wrong", Toast.LENGTH_SHORT)
            }
        )

        requestQueue.add(request)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_address, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.radio_button_address.setOnCheckedChangeListener(null)
        var address = addressList[position]
        holder.bind(address)
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    fun setAddress(list: ArrayList<Address>) {
        addressList = list
        notifyDataSetChanged()
    }

    fun getSelectedPosition() : Address{
        return addressList[lastCheckedPosition]
    }

    fun getAddresses() {
        val requestQueue = Volley.newRequestQueue(mContext)
        val request = StringRequest(
            Request.Method.GET,
            Endpoint.getAddressById(sm.getUserId()!!),
            {
                var gson = Gson()
                var addressResponse = gson.fromJson(it, AddressResponse::class.java)
                setAddress(ArrayList(addressResponse.data))
            },
            {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT)
            }
        )
        requestQueue.add(request)
    }
}