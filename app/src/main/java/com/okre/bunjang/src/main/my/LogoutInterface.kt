package com.okre.bunjang.src.main.my

import com.okre.bunjang.src.main.my.model.LogoutResponse

interface LogoutInterface {

    fun onPostLogoutSuccess(response: LogoutResponse)

    fun onPostLogoutFailure(message: String)

}