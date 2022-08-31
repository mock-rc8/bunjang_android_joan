package com.okre.bunjang.src.main.register

import android.os.Bundle
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityRegisterCategoryBinding

class RegisterCategoryActivity : BaseActivity<ActivityRegisterCategoryBinding>(ActivityRegisterCategoryBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.register_category_container, RegisterCategoryContainFragment(0)).commit()
    }
}