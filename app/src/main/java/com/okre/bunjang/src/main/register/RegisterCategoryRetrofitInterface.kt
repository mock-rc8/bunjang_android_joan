package com.okre.bunjang.src.main.register

import com.okre.bunjang.src.main.register.model.MainCategoryResponse
import com.okre.bunjang.src.main.register.model.SubCategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RegisterCategoryRetrofitInterface {
    @GET("/bunjang/category/main-category")
    fun getMain() : Call<MainCategoryResponse>

    @GET("/bunjang/category/main-category/{mainCategoryIdx}")
    fun getSub(
        @Path(value = "mainCategoryIdx", encoded = true)
        mainCategoryIdx : Int
    ) : Call<SubCategoryResponse>
}