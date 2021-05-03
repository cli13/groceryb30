package com.example.grocery.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.grocery.fragments.SubCategoryFragment
import com.example.grocery.models.SubCategory

class MyPageFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var mFragment = ArrayList<Fragment>()
    var mTitle = ArrayList<String>()

    override fun getCount(): Int {
        return mFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragment[position]
    }

    fun addFragments(categories: List<SubCategory>){
        for (category in categories){
            mTitle.add(category.subName)
            mFragment.add(SubCategoryFragment.newInstance(category))
        }
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }

}