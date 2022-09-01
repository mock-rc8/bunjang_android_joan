package com.okre.bunjang.src.main.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityRegisterTagBinding

class RegisterTagActivity : BaseActivity<ActivityRegisterTagBinding>(ActivityRegisterTagBinding::inflate) {

    var tagContent : String = ""
    var tagCount = 0

    lateinit var registerShared : SharedPreferences
    lateinit var registerEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tagChangeWatcher()

        checkBtnClick()

    }

    fun tagChangeWatcher() {
        binding.registerTagEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty() && p0.toString() != tagContent) {
                    tagContent = p0.toString()
                    val last = tagContent.substring(tagContent.length - 1, tagContent.length)
                    val noLast = tagContent.substring(0, tagContent.length-1)
                    if (last.contains(" ") && tagCount < 4) {
                        tagContent = noLast + last + "#"
                        binding.registerTagEdt.setText(tagContent)
                        binding.registerTagEdt.setSelection(tagContent.length)
                        tagCount++
                    } else if (last.contains(" ") && tagCount >= 4) {
                        tagContent = noLast
                    }
                }
            }
        })
    }

    fun checkBtnClick() {
        binding.registerTagCheck.setOnClickListener {
            if (binding.registerTagEdt.text.isNullOrEmpty()) {
                tagContent = ""
            } else {
                tagContent = "#" + binding.registerTagEdt.text.toString()
            }

            registerShared = getSharedPreferences("register", MODE_PRIVATE)
            registerEditor = registerShared.edit()

            registerEditor.putString("tag", tagContent)
            registerEditor.apply()

            finish()
        }
    }

}