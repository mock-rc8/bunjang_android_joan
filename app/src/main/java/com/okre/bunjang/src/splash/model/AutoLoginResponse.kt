package com.okre.bunjang.src.splash.model

data class AutoLoginResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: AutoLoginResult
)

data class AutoLoginResult(
    val status: String,
    val userIdx: Int
)