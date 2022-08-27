package com.okre.bunjang.src.main.home

import com.okre.bunjang.src.main.home.model.RecommendResponse
import retrofit2.Call
import retrofit2.http.GET

interface RecommendRetrofitInterface {
    @GET("/bunjang/products")
    fun getRecommend(): Call<RecommendResponse>
}