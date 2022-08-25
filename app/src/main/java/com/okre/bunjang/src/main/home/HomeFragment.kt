package com.okre.bunjang.src.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentHomeBinding
import com.okre.bunjang.src.main.home.adapter.HomeAdViewPagerAdapter
import com.okre.bunjang.src.main.home.adapter.HomeCategoryAdapter
import com.okre.bunjang.src.main.home.adapter.HomeRecommendViewPagerAdapter
import com.okre.bunjang.src.main.home.item.HomeCategoryItem

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    var homeAdViewPagerList = mutableListOf<Int>()
    private lateinit var handler : Handler
    private lateinit var thread : Thread
    private var currentPosition = 0
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

        adViewPager()

        handler = Handler(Looper.getMainLooper()) {
            adViewPagerChange()
            true
        }

        thread = Thread(PagerRunnable())
        thread.start()

        categoryRecyclerview()
        recommendViewPager()

    }

    fun adViewPager() {
        homeAdViewPagerList.add(R.drawable.image_ad_01)
        homeAdViewPagerList.add(R.drawable.image_ad_02)
        homeAdViewPagerList.add(R.drawable.image_ad_03)
        homeAdViewPagerList.add(R.drawable.image_ad_04)
        homeAdViewPagerList.add(R.drawable.image_ad_05)
        homeAdViewPagerList.add(R.drawable.image_ad_06)
        homeAdViewPagerList.add(R.drawable.image_ad_07)
        binding.homeAdViewpager.adapter = HomeAdViewPagerAdapter(homeAdViewPagerList)
        binding.homeAdViewpagerCount.text = getString(R.string.home_viewpager_count, 1, homeAdViewPagerList.size)
    }

    fun adViewPagerChange() {
        if (currentPosition == 7) {
            currentPosition = 0
        }
        binding.homeAdViewpager.setCurrentItem(currentPosition, false)
        currentPosition++

        binding.homeAdViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.homeAdViewpagerCount.text = getString(R.string.home_viewpager_count, position + 1, homeAdViewPagerList.size)
                currentPosition = position
            }
        })
    }

    inner class PagerRunnable : Runnable{
        override fun run() {
            while(true){
                try {
                    Thread.sleep(3000)
                    handler.sendEmptyMessage(0)
                } catch (e : InterruptedException){
                    Log.d("error", e.message.toString())
                }
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        // 전체화면 취소
        requireActivity().setStatusBarOrigin()
    }

}