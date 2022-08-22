package com.okre.bunjang.src.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityLoginPhoneBinding

class LoginPhoneActivity : BaseActivity<ActivityLoginPhoneBinding>(ActivityLoginPhoneBinding::inflate) {

    var item : MutableList<LoginPhoneTelecomItem> = arrayListOf()
    private lateinit var telecomBottomSheetDialog : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginPhoneBack.setOnClickListener {
            finish()
        }

        // 이름 입력
        nameEdittext()

        // 생년월일 입력
        birthEdittext()

        // 생년월일 - 성별 입력
        birthGenderEdittext()

        // 통신사
        telecomSelect()
    }

    fun nameEdittext() {
        binding.loginPhoneEdittextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.loginPhoneButtonNext.isEnabled = p0 != null && !p0.toString().equals("")
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.loginPhoneButtonNext.setOnClickListener {
            binding.loginPhoneLayoutBirth.visibility = View.VISIBLE
            binding.loginPhoneEdittextBirth.requestFocus()
            binding.loginPhoneTitleTop.setText(R.string.login_phone_title_birth_top)
            binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_birth_bottom)
            binding.loginPhoneButtonNext.setText(R.string.login_phone_button_next_check)
            binding.loginPhoneButtonNext.isEnabled = false
        }
    }

    fun birthEdittext() {
        binding.loginPhoneEdittextBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.loginPhoneEdittextBirth.length() == 6) {
                    binding.loginPhoneEdittextBirthGender.requestFocus()
                }
            }
        })
    }

    fun birthGenderEdittext() {
        binding.loginPhoneEdittextBirthGender.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.loginPhoneEdittextBirthGender.length() == 1) {
                    binding.loginPhoneLayoutTelecom.visibility = View.VISIBLE
                    binding.loginPhoneTitleTop.setText(R.string.login_phone_title_telecom_top)
                    binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_telecom_bottom)
                    telecomBottomSheetDialog.show()
                }
            }
        })
    }

    fun telecomSelect() {
        //bottomsheetdialog&recyclerview 생성
        val telecomBottomSheetView = layoutInflater.inflate(R.layout.dialog_login_phone_telecom, null)
        val telecomRecyclerView = telecomBottomSheetView.findViewById<RecyclerView>(R.id.rv_login_phone_telecom)
        item.add(LoginPhoneTelecomItem("SKT", false))
        item.add(LoginPhoneTelecomItem("KT", false))
        item.add(LoginPhoneTelecomItem("LG U+", false))
        item.add(LoginPhoneTelecomItem("SKT 알뜰폰", false))
        item.add(LoginPhoneTelecomItem("KT 알뜰폰", false))
        item.add(LoginPhoneTelecomItem("LG U+ 알뜰폰", false))
        val rvadapterLoginPhoneTelecom = LoginPhoneTelecomAdapter(item)
        telecomRecyclerView.adapter = rvadapterLoginPhoneTelecom
        telecomBottomSheetDialog = BottomSheetDialog(this)
        telecomBottomSheetDialog.setContentView(telecomBottomSheetView)

        binding.loginPhoneLayoutTelecom.setOnClickListener {
            telecomBottomSheetDialog.show()
        }

        rvadapterLoginPhoneTelecom.itemClick = object : LoginPhoneTelecomAdapter.ItemClick {
            override fun onClick(view: View, position: Int, text: String) {
                binding.loginPhoneButtonTelecom.setText(text)
                binding.loginPhoneButtonTelecom.setTextColor(ContextCompat.getColor(this@LoginPhoneActivity, R.color.black))
                telecomBottomSheetDialog.dismiss()
                binding.loginPhoneLayoutPhoneNumber.visibility = View.VISIBLE
                binding.loginPhoneTitleTop.setText(R.string.login_phone_title_number_top)
                binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_number_bottom)
                binding.loginPhoneEdittextPhoneNumber.requestFocus()
            }
        }


    }


}