package com.okre.bunjang.src.splash

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.splash.model.AutoLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class AutoLoginService(val autoLoginInterface: AutoLoginInterface) {

    val autoLoginRetrofitInterface = ApplicationClass.sRetrofit.create(AutoLoginRetrofitInterface::class.java)

    fun tryGetAutoLogin() {
        autoLoginRetrofitInterface.getAutoLogin().enqueue(object : Callback<AutoLoginResponse> {
            override fun onResponse(
                call: Call<AutoLoginResponse>,
                response: Response<AutoLoginResponse>
            ) {
                autoLoginInterface.onGetAutoLoginSuccess(response.body() as AutoLoginResponse)
            }

            override fun onFailure(call: Call<AutoLoginResponse>, t: Throwable) {
                autoLoginInterface.onGetAutoLoginFailure(t.message ?: "통신 오류")
            }

        })
    }
}