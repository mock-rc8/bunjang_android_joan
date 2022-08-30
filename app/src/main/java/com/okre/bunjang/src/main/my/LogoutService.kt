package com.okre.bunjang.src.main.my

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.my.model.LogoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LogoutService(val logoutInterface: LogoutInterface) {

    val logoutRetrofitInterface = ApplicationClass.sRetrofit.create(LogoutRetrofitInterface::class.java)

    fun tryPostLogout() {
        logoutRetrofitInterface.postLogout().enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                logoutInterface.onPostLogoutSuccess(response.body() as LogoutResponse)
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                logoutInterface.onPostLogoutFailure(t.message ?: "통신 오류")
            }
        })
    }
}