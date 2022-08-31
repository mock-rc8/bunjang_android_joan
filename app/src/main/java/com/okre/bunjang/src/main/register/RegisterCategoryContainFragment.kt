package com.okre.bunjang.src.main.register

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentRegisterCategoryContainBinding

class RegisterCategoryContainFragment(depth : Int) : BaseFragment<FragmentRegisterCategoryContainBinding>(FragmentRegisterCategoryContainBinding::bind, R.layout.fragment_register_category_contain) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNavigation(true)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.register_category_container, RegisterCategoryContainFragment(0)).commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideBottomNavigation(false)
    }

    fun hideBottomNavigation(boolean: Boolean) {
        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.main_btm_nav)
        if (boolean) {
            bottomNavigation.visibility = View.GONE
        } else {
            bottomNavigation.visibility = View.VISIBLE
        }
    }

}