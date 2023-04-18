package com.example.thistothat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // create
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get elements
        val tabLayout : TabLayout = findViewById(R.id.tabs)
        val viewPager2 : ViewPager2 = findViewById(R.id.view_pager)

        val adapter = ViewPagerAdapter(this)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position){
                0 -> tab.text = "Favorites"
                1 -> tab.text = "All"
                else -> throw IllegalArgumentException("Invalid position CODE: 001")
            }
        }.attach()
    }
}