package com.okre.bunjang.src.login.model

import com.google.gson.annotations.SerializedName
import com.okre.bunjang.config.BaseResponse

data class LoginResponse(
    @SerializedName("result") val result: ResultLogin
) : BaseResponse()

data class ResultLogin(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("status") val status: String,
    @SerializedName("userIdx") val userIdx: Int
)