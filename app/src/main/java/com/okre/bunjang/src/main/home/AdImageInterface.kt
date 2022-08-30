package com.okre.bunjang.src.main.home

import com.okre.bunjang.src.main.home.model.AdImageResponse


interface AdImageInterface {
    fun onGetAdImageSuccess(response: AdImageResponse)

    fun onGetAdImageFailure(message: String)
}