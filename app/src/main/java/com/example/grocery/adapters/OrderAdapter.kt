package com.example.grocery.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.activites.OrderDetails
import com.example.grocery.apps.Config.Companion.convertMongoDate
import com.example.grocery.models.OrderData
import kotlinx.android.synthetic.main.row_layout_order.view.*

class OrderAdapter(var mContext : Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    var orderList = ArrayList<OrderData>()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        fun bind(o : OrderData){
            itemView.text_view_order_date.text = convertMongoDate(o.date)
            var productString = ""
            for(product in o.products){
                productString += "${product.quantity} X ${product.productName}\n"
            }
            itemView.text_view_order_price.text = "$${o.orderSummary.totalAmount}"
            itemView.text_view_order_products.text = productString
            itemView.text_view_order_status.text = o.payment.paymentStatus

            itemView.setOnClickListener {
                var intent = Intent(mContext,OrderDetails::class.java)
                intent.putExtra(OrderData.KEY_ORDER, o)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_order, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = orderList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setOrder(list : ArrayList<OrderData>){
        orderList = list
        notifyDataSetChanged()
    }
}