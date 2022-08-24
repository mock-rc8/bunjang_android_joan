package com.okre.bunjang.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentHomeRecommendBinding
import com.okre.bunjang.src.login.adpater.LoginPhoneTelecomAdapter
import com.okre.bunjang.src.main.home.adapter.HomeRecommendAdapter

class HomeRecommendFragment : BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::bind, R.layout.fragment_home_recommend) {

    private val rvAdapter = HomeRecommendAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recommendRecyclerView()

    }

    fun recommendRecyclerView() {
        binding.homeRvRecommend.adapter = rvAdapter

        rvAdapter.itemClick = object : HomeRecommendAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(activity, HomeRecommendProductDetailActivity::class.java)
                //intent에 아이템 코드 보내기
                startActivity(intent)
            }
        }
    }
}