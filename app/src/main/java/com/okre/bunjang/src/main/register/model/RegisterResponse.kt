package com.okre.bunjang.src.main.register.model

data class RegisterResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RegisterResult
)

data class RegisterResult(
    val productsIdx: Int,
    val userIdx: Int
)