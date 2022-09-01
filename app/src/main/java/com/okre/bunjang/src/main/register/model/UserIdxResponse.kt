package com.okre.bunjang.src.main.register.model

data class UserIdxResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: UserIdxResult
)

data class UserIdxResult(
    val userIdx: Int
)