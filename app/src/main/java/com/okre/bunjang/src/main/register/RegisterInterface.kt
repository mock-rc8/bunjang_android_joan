package com.okre.bunjang.src.main.register

import com.okre.bunjang.src.main.register.model.RegisterResponse
import com.okre.bunjang.src.main.register.model.UserIdxResponse

interface RegisterInterface {

    fun onGetUserIdxSuccess(response: UserIdxResponse)

    fun onGetUserIdxFailure(message: String)

    fun onPostNewSuccess(response: RegisterResponse)

    fun onPostNewFailure(message: String)
}