package com.okre.bunjang.src.main

import retrofit2.Call
import retrofit2.http.GET

interface UserInfoRetrofitInterface {
    @GET("/bunjang/users/store-names")
    fun getUserInfo(): Call<UserInfoResponse>

}