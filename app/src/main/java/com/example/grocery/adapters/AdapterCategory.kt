package com.example.grocery.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.activites.SubCategory
import com.example.grocery.apps.Endpoint
import com.example.grocery.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_category.view.*

class AdapterCategory(var mContext: Context) : RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    private var categoryList = ArrayList<Category>()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(c: Category) {
            itemView.text_view_category_name.text = c.catName

            Picasso.get().load(Endpoint.getImage(c.catImage))
                .into(itemView.image_view_category)

            itemView.setOnClickListener {
                var intent = Intent(mContext, SubCategory::class.java)
                intent.putExtra(Category.KEY_CATEGORY, c.catId)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_category, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var catogery = categoryList[position]
        holder.bind(catogery)
    }

    fun setList(list: ArrayList<Category>) {
        categoryList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}