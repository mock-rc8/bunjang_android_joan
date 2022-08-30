package com.okre.bunjang.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.okre.bunjang.R
import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.config.ApplicationClass.Companion.loginSharedPreferences
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivitySplashBinding
import com.okre.bunjang.src.login.LoginActivity
import com.okre.bunjang.src.login.LoginService
import com.okre.bunjang.src.main.MainActivity
import com.okre.bunjang.src.splash.model.AutoLoginResponse

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate),
    AutoLoginInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jwtToken: String? = loginSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        val animation = AnimationUtils.loadAnimation(this, R.anim.scale)
        binding.splashImage.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            if (jwtToken != null) {// token o -> 자동로그인
                showLoadingDialog(this)
                AutoLoginService(this).tryGetAutoLogin()
            } else {// token x -> 처음이거나 로그인
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1500)
    }

    override fun onGetAutoLoginSuccess(response: AutoLoginResponse) {
        dismissLoadingDialog()
        if (response.code == 1000) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onGetAutoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}