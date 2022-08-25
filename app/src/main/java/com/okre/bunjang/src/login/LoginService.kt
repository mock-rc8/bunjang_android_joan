package com.okre.bunjang.src.login

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.login.model.LoginRequest
import com.okre.bunjang.src.login.model.LoginResponse
import com.okre.bunjang.src.login.model.SignRequest
import com.okre.bunjang.src.login.model.SignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginActivityInterface: LoginActivityInterface) {

    val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)

    fun tryPostSignUp(signRequest: SignRequest) {
        loginRetrofitInterface.postSignup(signRequest).enqueue(object : Callback<SignResponse> {
            override fun onResponse(call: Call<SignResponse>, response: Response<SignResponse>) {
                loginActivityInterface.onPostSignUpSuccess(response.body() as SignResponse)
            }

            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                loginActivityInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostLogin(loginRequest: LoginRequest) {
        loginRetrofitInterface.postLogin(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginActivityInterface.onPostLoginSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginActivityInterface.onPostLoginFailure(t.message ?: "통신 오류")
            }

        })
    }

}