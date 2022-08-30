package com.okre.bunjang.src.main.home

import com.okre.bunjang.src.main.home.model.ProductDetailResponse
import com.okre.bunjang.src.main.home.model.RecommendHeartResponse
import com.okre.bunjang.src.main.home.model.RecommendResponse

interface RecommendFragmentInterface {

    fun onGetRecommendSuccess(response: RecommendResponse)

    fun onGetRecommendFailure(message: String)

    fun onGetProductDetailSuccess(response: ProductDetailResponse)

    fun onGetProductDetailFailure(message: String)

    fun onPostHeartSuccess(response: RecommendHeartResponse)

    fun onPostHeartDetailFailure(message: String)

    fun onPatchHeartSuccess(response: RecommendHeartResponse)

    fun onPatchHeartDetailFailure(message: String)

}