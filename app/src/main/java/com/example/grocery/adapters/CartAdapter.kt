package com.example.grocery.adapters

import android.content.Context
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.apps.Endpoint
import com.example.grocery.managers.DBHelper
import com.example.grocery.managers.SessionManager
import com.example.grocery.models.CartProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_cart.view.*

class CartAdapter(var mContext: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var cartList = ArrayList<CartProduct>()
    private var db = DBHelper(mContext)
    private var sm = SessionManager(mContext)

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(cp: CartProduct) {
            Picasso.get().load(Endpoint.getImage(cp.image)).into(itemView.image_view_product)

            itemView.text_view_price.text = "$" + (cp.price * cp.quantity).toString()
            itemView.text_view_product_name.text = cp.name.toString()
            itemView.text_view_quantity_number.text = cp.quantity.toString()

            itemView.image_view_add.setOnClickListener {
                db.onEditCartQuantity(cp._id, CartProduct.MODE_ADD)
                updateData()
            }
            itemView.image_view_sub.setOnClickListener {
                db.onEditCartQuantity(cp._id, CartProduct.MODE_MINUS)
                updateData()
            }
            itemView.image_view_cancel.setOnClickListener {
                db.deleteItemById(cp._id)
                updateData()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_cart, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = cartList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun updateData() {
        cartList = ArrayList(db.getAllFromCart())
        notifyDataSetChanged()
    }
}