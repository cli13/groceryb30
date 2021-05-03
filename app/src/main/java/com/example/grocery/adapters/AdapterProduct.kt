package com.example.grocery.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.activites.OrderDetails
import com.example.grocery.activites.ProductDetails
import com.example.grocery.apps.Endpoint
import com.example.grocery.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_product.view.*

class AdapterProduct(var mContext: Context) : RecyclerView.Adapter<AdapterProduct.ViewHolder>() {

    var productList = ArrayList<Product>()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p : Product) {
            Picasso.get().load(Endpoint.getImage(p.image))
                .into(itemView.image_view_product)

            itemView.text_view_product_name.text = p.productName
            itemView.text_view_product_discount.text = "$${p.price.toString()}"
            itemView.text_view_product_original.text = "$${p.mrp.toString()}"
            itemView.text_view_product_original.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductDetails::class.java)
                intent.putExtra(Product.KEY_PRODUCT, p)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_product, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = productList[position]
        holder.bind(product)
    }

    fun setList(list : ArrayList<Product>){
        productList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}