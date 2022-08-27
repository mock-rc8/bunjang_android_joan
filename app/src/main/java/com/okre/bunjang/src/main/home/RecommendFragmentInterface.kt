package com.okre.bunjang.src.main.home

import com.okre.bunjang.src.main.home.model.RecommendResponse

interface RecommendFragmentInterface {

    fun onGetRecommendSuccess(response: RecommendResponse)

    fun onGetRecommendFailure(message: String)

}