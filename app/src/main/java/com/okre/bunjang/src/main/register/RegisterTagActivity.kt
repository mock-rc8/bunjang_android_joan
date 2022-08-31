package com.okre.bunjang.src.main.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityRegisterTagBinding

class RegisterTagActivity : BaseActivity<ActivityRegisterTagBinding>(ActivityRegisterTagBinding::inflate) {

    var tagContent : String = ""
    var tagCount = 0

    lateinit var registerShared : SharedPreferences
    lateinit var registerEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tagEnter()

        tagChangeWatcher()

        checkBtnClick()

    }

    fun tagEnter() {
        binding.registerTagEdt.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_SPACE && keyEvent.action == KeyEvent.ACTION_UP && tagCount <= 3) {
                binding.registerTagEdt.setText("$tagContent #")
                tagCount++
            }
            true
        }
    }

    fun tagChangeWatcher() {
        binding.registerTagEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                tagContent = binding.registerTagEdt.text.toString()
            }
        })
    }

    fun checkBtnClick() {
        if (binding.registerTagEdt.text.isNullOrEmpty()) {
            tagContent = ""
        } else {
            tagContent = "#" + binding.registerTagEdt.text.toString()
        }

        registerShared = getSharedPreferences("register", MODE_PRIVATE)
        registerEditor = registerShared.edit()

        registerEditor.putString("tag", tagContent)
        registerEditor.apply()

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

}