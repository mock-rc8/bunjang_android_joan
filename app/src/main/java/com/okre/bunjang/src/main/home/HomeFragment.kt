package com.okre.bunjang.src.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentHomeBinding
import com.okre.bunjang.src.main.home.adapter.HomeCategoryAdapter
import com.okre.bunjang.src.main.home.adapter.HomeRecommendViewPagerAdapter
import com.okre.bunjang.src.main.home.item.HomeCategoryItem

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    var categoryList = mutableListOf<HomeCategoryItem>()
    private val tabTitleArray = arrayOf("추천상품", "브랜드")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상태 바 투명으로 설정(전체화면)
        requireActivity().setStatusBarTransparent()

        binding.homeToolbar.setPadding(
            0,
            requireContext().statusBarHeight(),
            0,
            0
        )

        categoryRecyclerview()
        recommendViewPager()

    }

    fun categoryRecyclerview() {
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_0, "찜"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_1, "폴로"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_2, "최근본상품"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_3, "여성가방"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_4, "내피드"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_5, "스니커즈"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_6, "내폰시세"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_7, "스타굿즈"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_8, "우리동네"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_9, "캠핑"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_10, "친구초대"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_11, "골프"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_12, "전체메뉴"))
        categoryList.add(HomeCategoryItem(R.drawable.icon_home_category_13, "피규어/인형"))

        binding.homeRvCategory.adapter = HomeCategoryAdapter(categoryList)

    }

    fun recommendViewPager() {
        binding.homeViewpager.adapter = HomeRecommendViewPagerAdapter(parentFragmentManager, lifecycle)

        TabLayoutMediator(binding.homeTablayout, binding.homeViewpager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

}