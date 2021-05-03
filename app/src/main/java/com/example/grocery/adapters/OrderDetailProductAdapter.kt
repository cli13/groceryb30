package com.example.grocery.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.apps.Endpoint
import com.example.grocery.models.ProductOrder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_order_detail_product.view.*

class OrderDetailProductAdapter(var mContext: Context) :
    RecyclerView.Adapter<OrderDetailProductAdapter.ViewHolder>() {

    var product = ArrayList<ProductOrder>()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p: ProductOrder) {
            Picasso.get().load(Endpoint.getImage(p.image)).into(itemView.image_view_product)

            itemView.text_view_product_name.text = p.productName
            itemView.text_view_mrp.text = "$${p.mrp * p.quantity}"
            itemView.text_view_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_price.text = "$${p.price * p.quantity}"
            itemView.text_view_quantity.text = "Qty: ${p.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(mContext)
            .inflate(R.layout.row_layout_order_detail_product, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = product[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return product.size
    }

    fun setList(list : ArrayList<ProductOrder>){
        product = list
        notifyDataSetChanged()
    }
}