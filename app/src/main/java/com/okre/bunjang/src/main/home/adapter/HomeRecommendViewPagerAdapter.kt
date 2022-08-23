package com.okre.bunjang.src.main.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.okre.bunjang.src.main.home.HomeBrandFragment
import com.okre.bunjang.src.main.home.HomeRecommendFragment

private val NUM_TABS = 2

class HomeRecommendViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return HomeRecommendFragment()
            1 -> return HomeBrandFragment()
        }
        return HomeRecommendFragment()
    }
}