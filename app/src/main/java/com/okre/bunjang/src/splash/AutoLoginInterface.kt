package com.okre.bunjang.src.splash

import com.okre.bunjang.src.login.model.SignResponse
import com.okre.bunjang.src.splash.model.AutoLoginResponse

interface AutoLoginInterface {

    fun onGetAutoLoginSuccess(response: AutoLoginResponse)

    fun onGetAutoLoginFailure(message: String)

}