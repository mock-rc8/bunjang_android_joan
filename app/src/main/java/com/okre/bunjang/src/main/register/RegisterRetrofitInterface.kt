package com.okre.bunjang.src.main.register

import com.okre.bunjang.src.main.register.model.RegisterRequest
import com.okre.bunjang.src.main.register.model.RegisterResponse
import com.okre.bunjang.src.main.register.model.UserIdxResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterRetrofitInterface {
    @GET("/bunjang/stores/store-id")
    fun getUserIdx() : Call<UserIdxResponse>

    @POST("/bunjang/products/new")
    fun postNew(
        @Body
        params: RegisterRequest
    ) : Call<RegisterResponse>
}