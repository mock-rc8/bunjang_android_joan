package com.okre.bunjang.src.main.my.model

data class LogoutResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: LogoutResult
)

data class LogoutResult(
    val logoutMessage: String,
    val status: String,
    val userIdx: Int
)