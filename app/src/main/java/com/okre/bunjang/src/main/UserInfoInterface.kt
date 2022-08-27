package com.okre.bunjang.src.main

interface UserInfoInterface {

    fun onGetUserInfoSuccess(response: UserInfoResponse)

    fun onGetUserInfoFailure(message: String)

}