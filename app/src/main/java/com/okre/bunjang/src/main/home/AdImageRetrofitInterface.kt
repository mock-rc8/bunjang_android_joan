package com.okre.bunjang.src.main.home

import com.okre.bunjang.src.main.home.model.AdImageResponse
import retrofit2.Call
import retrofit2.http.GET

interface AdImageRetrofitInterface {
    @GET("/bunjang/products/ad-image")
    fun getAdImage() : Call<AdImageResponse>
}