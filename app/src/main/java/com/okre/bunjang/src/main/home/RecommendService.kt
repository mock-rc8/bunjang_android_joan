package com.okre.bunjang.src.main.home

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.home.model.ProductDetailResponse
import com.okre.bunjang.src.main.home.model.RecommendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RecommendService(val recommendFragmentInterface: RecommendFragmentInterface) {

    val recommendRetrofitInterface = ApplicationClass.sRetrofit.create(RecommendRetrofitInterface::class.java)

    fun tryGetRecommend() {
        recommendRetrofitInterface.getRecommend().enqueue(object : Callback<RecommendResponse> {
            override fun onResponse(
                call: Call<RecommendResponse>,
                response: Response<RecommendResponse>
            ) {
                recommendFragmentInterface.onGetRecommendSuccess(response.body() as RecommendResponse)
            }

            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                recommendFragmentInterface.onGetRecommendFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetProductDetail(productIdx : Int) {
        recommendRetrofitInterface.getProductDetail(productIdx).enqueue(object : Callback<ProductDetailResponse> {
            override fun onResponse(
                call: Call<ProductDetailResponse>,
                response: Response<ProductDetailResponse>
            ) {
                recommendFragmentInterface.onGetProductDetailSuccess(response.body() as ProductDetailResponse)
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                recommendFragmentInterface.onGetProductDetailFailure(t.message ?: "통신 오류")
            }

        })
    }

}