package com.okre.bunjang.src.main.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.okre.bunjang.R
import com.okre.bunjang.config.ApplicationClass.Companion.loginSPEditor
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentMyBinding
import com.okre.bunjang.src.login.LoginActivity
import com.okre.bunjang.src.login.LoginService
import com.okre.bunjang.src.main.my.model.LogoutResponse

class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::bind, R.layout.fragment_my),
    LogoutInterface{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBtn.setOnClickListener {
            showLoadingDialog(requireContext())
            LogoutService(this).tryPostLogout()
        }
    }

    override fun onPostLogoutSuccess(response: LogoutResponse) {
        if (response.code == 1000) {
            // 로그아웃 시 token 삭제
            loginSPEditor.clear()
            loginSPEditor.apply()

            showCustomToast(response.result.logoutMessage)

            // 로그인화면으로 이동
            val intent = Intent(context, LoginActivity::class.java)
            requireActivity().finish()
            startActivity(intent)
        }
    }

    override fun onPostLogoutFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}