package com.okre.bunjang.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.okre.bunjang.R
import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivitySplashBinding
import com.okre.bunjang.src.login.LoginActivity
import com.okre.bunjang.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jwtToken: String? = ApplicationClass.loginSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        val animation = AnimationUtils.loadAnimation(this, R.anim.scale)
        binding.splashImage.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            if (jwtToken != null) {// 로그인 o
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {// 로그인 x
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1500)
    }
}