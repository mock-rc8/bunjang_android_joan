package com.okre.bunjang.src.login

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.login.model.SignRequest
import com.okre.bunjang.src.login.model.SignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginActivityInterface: LoginActivityInterface) {

    fun tryPostSignUp(signRequest: SignRequest) {
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postSignup(signRequest).enqueue(object : Callback<SignResponse> {
            override fun onResponse(call: Call<SignResponse>, response: Response<SignResponse>) {
                loginActivityInterface.onPostSignUpSuccess(response.body() as SignResponse)
            }

            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                loginActivityInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }

        })
    }

}