package com.okre.bunjang.src.splash

import com.okre.bunjang.src.splash.model.AutoLoginResponse
import retrofit2.Call
import retrofit2.http.GET

interface AutoLoginRetrofitInterface {
    @GET("/bunjang/users/log-in/auto")
    fun getAutoLogin() : Call<AutoLoginResponse>
}