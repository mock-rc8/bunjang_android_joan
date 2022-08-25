package com.okre.bunjang.src.login

import com.okre.bunjang.src.login.model.SignResponse

interface LoginActivityInterface {

    fun onPostSignUpSuccess(response: SignResponse)

    fun onPostSignUpFailure(message: String)

}