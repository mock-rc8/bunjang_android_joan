package com.okre.bunjang.src.main

import com.okre.bunjang.config.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class UserInfoService(val userInfoInterface: UserInfoInterface) {

    val userInfoRetrofitInterface = ApplicationClass.sRetrofit.create(UserInfoRetrofitInterface::class.java)

    fun tryGetUserInfo() {
        userInfoRetrofitInterface.getUserInfo().enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                userInfoInterface.onGetUserInfoSuccess(response.body() as UserInfoResponse)
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                userInfoInterface.onGetUserInfoFailure(t.message ?: "통신 오류")
            }

        })
    }
}