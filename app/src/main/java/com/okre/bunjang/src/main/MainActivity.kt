package com.okre.bunjang.src.main

import android.os.Bundle
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityMainBinding
import com.okre.bunjang.src.main.home.HomeFragment
import com.okre.bunjang.src.main.lightningTalk.LightningTalkFragment
import com.okre.bunjang.src.main.my.MyFragment
import com.okre.bunjang.src.main.register.RegisterFragment
import com.okre.bunjang.src.main.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, HomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_search -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, SearchFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_register -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, RegisterFragment())
                            .commitAllowingStateLoss()
                        //val intent = Intent(context, RegisterActivity::class.java)
                        //context.startActivity(intent)
                    }
                    R.id.menu_main_btm_nav_lightning_talk -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, LightningTalkFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_my -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, MyFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            // selectedItemId = R.id.menu_main_btm_nav_home
            // 기존에 있던 homeFragment가 사라지고, onDestroyView가 호출되므로 사용하지 말 것!!
        }
    }
}