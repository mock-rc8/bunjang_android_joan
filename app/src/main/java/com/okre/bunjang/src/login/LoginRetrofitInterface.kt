package com.okre.bunjang.src.login

import com.okre.bunjang.src.login.model.SignRequest
import com.okre.bunjang.src.login.model.SignResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/bunjang/users")
    fun postSignup(
        @Body
        params: SignRequest
    ): Call<SignResponse>
}