package com.okre.bunjang.src.main.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.okre.bunjang.R
import com.okre.bunjang.config.ApplicationClass.Companion.loginSPEditor
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentMyBinding
import com.okre.bunjang.src.login.LoginActivity
import com.okre.bunjang.src.login.LogoutInterface
import com.okre.bunjang.src.main.MainActivity

class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::bind, R.layout.fragment_my) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBtn.setOnClickListener {
            // 로그아웃 시 token 삭제
            loginSPEditor.clear()
            loginSPEditor.apply()

            //LogoutInterface.logoutBtnPush(true)

            // 로그인화면으로 이동
            val intent = Intent(context, LoginActivity::class.java)
            requireActivity().finish()
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

        }
    }



}