package com.okre.bunjang.src.main.my

import com.okre.bunjang.src.main.my.model.LogoutResponse
import retrofit2.Call
import retrofit2.http.POST

interface LogoutRetrofitInterface {
    @POST("/bunjang/users/log-out")
    fun postLogout() : Call<LogoutResponse>
}