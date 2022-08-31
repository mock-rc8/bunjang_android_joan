package com.okre.bunjang.src.main.register

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentRegisterCategoryContainBinding
import com.okre.bunjang.src.main.home.AdImageService
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAreaSelectAdapter
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressManageResult
import com.okre.bunjang.src.main.register.adapter.RegisterCategoryAdapter
import com.okre.bunjang.src.main.register.item.CategoryResult
import com.okre.bunjang.src.main.register.model.MainCategoryResponse
import com.okre.bunjang.src.main.register.model.SubCategoryResponse

class RegisterCategoryContainFragment(depth : Int) : BaseFragment<FragmentRegisterCategoryContainBinding>(FragmentRegisterCategoryContainBinding::bind, R.layout.fragment_register_category_contain),
    RegisterCategoryInterface {

    private var depth = depth
    var categoryItem : MutableList<CategoryResult> = arrayListOf()
    var categoryAdapter = RegisterCategoryAdapter(categoryItem)

    lateinit var registerShared : SharedPreferences
    lateinit var registerEditor: SharedPreferences.Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerShared = requireContext().getSharedPreferences("register", AppCompatActivity.MODE_PRIVATE)
        registerEditor = registerShared.edit()

        when(depth) {
            0 -> {
                binding.categoryTitleDivider.visibility = View.GONE
                binding.categorySubTitle.visibility = View.GONE
                showLoadingDialog(requireContext())
                RegisterCategoryService(this).tryGetMain()
            }
            1 -> {
                binding.categoryTitleDivider.visibility = View.VISIBLE
                binding.categorySubTitle.visibility = View.VISIBLE
                binding.categorySubTitle.text = registerShared.getString("mainCategory", "")
                binding.categoryMainTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                showLoadingDialog(requireContext())
                val index = registerShared.getInt("mainIdx", 0)
                RegisterCategoryService(this).tryGetSub(index)
            }
        }
    }

    fun rvClick() {
        categoryAdapter.itemClick = object : RegisterCategoryAdapter.ItemClick {
            override fun onClick(view: View, position: Int, name: String, Idx: Int) {
                Log.d("dddd", "click2")
                when(depth) {
                    0 -> {
                        registerEditor.putString("mainCategory", name)
                        registerEditor.putInt("mainIdx", Idx)
                        registerEditor.apply()
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.register_category_container, RegisterCategoryContainFragment(1)).commit()
                    }
                    1 -> {
                        registerEditor.putString("subCategory", name)
                        registerEditor.apply()
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    override fun onGetMainSuccess(response: MainCategoryResponse) {
        for (list in response.result) {
            categoryItem.add(CategoryResult(list.categoryName, list.mainCategoryIdx, true))
        }
        categoryAdapter = RegisterCategoryAdapter(categoryItem)
        binding.categoryRvCategories.adapter = categoryAdapter
        rvClick()
        dismissLoadingDialog()
    }

    override fun onGetMainFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetSubSuccess(response: SubCategoryResponse) {
        for (list in response.result) {
            categoryItem.add(CategoryResult(list.categoryName, list.subCategoryIdx, false))
        }
        categoryAdapter = RegisterCategoryAdapter(categoryItem)
        binding.categoryRvCategories.adapter = categoryAdapter
        rvClick()
        dismissLoadingDialog()
    }

    override fun onGetSubFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}