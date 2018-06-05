package com.example.mygallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    // 뷰페이저가 표시할 프래그먼트 목록
    private val items = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    fun updateFragments(items : List<Fragment>) {
        this.items.clear()
        this.items.addAll(items)
    }

}