package com.okre.bunjang.src.login.model

import com.google.gson.annotations.SerializedName
import com.okre.bunjang.config.BaseResponse

data class SignResponse (
    @SerializedName("result") val result: ResultSignUp
) : BaseResponse()

data class ResultSignUp (
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("storeName") val storeName: String
)