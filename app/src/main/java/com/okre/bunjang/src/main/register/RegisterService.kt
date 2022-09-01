package com.okre.bunjang.src.main.register

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.register.model.RegisterRequest
import com.okre.bunjang.src.main.register.model.RegisterResponse
import com.okre.bunjang.src.main.register.model.UserIdxResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RegisterService(val registerInterface: RegisterInterface) {

    val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)

    fun tryGetUserIdx() {
        registerRetrofitInterface.getUserIdx().enqueue(object : Callback<UserIdxResponse> {
            override fun onResponse(
                call: Call<UserIdxResponse>,
                response: Response<UserIdxResponse>
            ) {
                registerInterface.onGetUserIdxSuccess(response.body() as UserIdxResponse)
            }

            override fun onFailure(call: Call<UserIdxResponse>, t: Throwable) {
                registerInterface.onGetUserIdxFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostNew(registerRequest: RegisterRequest) {
        registerRetrofitInterface.postNew(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                registerInterface.onPostNewSuccess(response.body() as RegisterResponse)
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerInterface.onPostNewFailure(t.message ?: "통신 오류")
            }

        })
    }
}