package com.okre.bunjang.src.login

import com.okre.bunjang.src.login.model.LoginResponse
import com.okre.bunjang.src.login.model.SignResponse

interface LoginActivityInterface {

    fun onPostSignUpSuccess(response: SignResponse)

    fun onPostSignUpFailure(message: String)

    fun onPostLoginSuccess(response: LoginResponse)

    fun onPostLoginFailure(message: String)

}