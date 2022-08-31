package com.okre.bunjang.src.main.register

import com.okre.bunjang.src.main.register.model.MainCategoryResponse
import com.okre.bunjang.src.main.register.model.SubCategoryResponse

interface RegisterCategoryInterface {

    fun onGetMainSuccess(response: MainCategoryResponse)

    fun onGetMainFailure(message: String)

    fun onGetSubSuccess(response: SubCategoryResponse)

    fun onGetSubFailure(message: String)
}