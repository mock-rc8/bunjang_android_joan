package com.okre.bunjang.src.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentHomeRecommendBinding
import com.okre.bunjang.src.main.home.adapter.HomeRecommendAdapter

class HomeRecommendFragment : BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::bind, R.layout.fragment_home_recommend) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recommendRecyclerView()
    }

    fun recommendRecyclerView() {
        binding.homeRvRecommend.adapter = HomeRecommendAdapter()
    }
}