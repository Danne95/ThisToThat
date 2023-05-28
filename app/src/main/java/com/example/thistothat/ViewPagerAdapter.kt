package com.example.thistothat

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    @NonNull
    override fun createFragment(position: Int): Fragment = when (position){
            0 -> Fragment2()
            1 -> Fragment1()
            else -> throw IllegalArgumentException("Invalid position CODE: 002")
        }

    override fun getItemCount(): Int { return 2}
}
