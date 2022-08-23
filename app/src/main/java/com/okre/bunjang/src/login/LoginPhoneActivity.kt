package com.okre.bunjang.src.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityLoginPhoneBinding
import com.okre.bunjang.src.login.adpater.LoginPhoneTelecomAdapter
import com.okre.bunjang.src.login.adpater.LoginPhoneTermsAdapter
import com.okre.bunjang.src.login.item.LoginPhoneTelecomItem
import com.okre.bunjang.src.login.item.LoginPhoneTermsItem
import com.okre.bunjang.src.main.MainActivity

class LoginPhoneActivity : BaseActivity<ActivityLoginPhoneBinding>(ActivityLoginPhoneBinding::inflate) {

    var item : MutableList<LoginPhoneTelecomItem> = arrayListOf()
    private lateinit var telecomBottomSheetDialog : BottomSheetDialog
    var itemTerms : MutableList<LoginPhoneTermsItem> = arrayListOf()
    private lateinit var termsBottomSheetDialog : BottomSheetDialog
    private var firstClick = false
    private var lastClick = false

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

        // 휴대폰 번호
        phoneNumberEdittext()

        // 비밀번호
        passwordEdittext()

        // 상점명
        shopNameEdittext()

        // 약관 동의
        termsSelect()

    }

    fun nameEdittext() {
        binding.loginPhoneEdittextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.loginPhoneButtonNext.isEnabled = p0 != null && !p0.toString().equals("")
            }

            override fun afterTextChanged(p0: Editable?) {
                firstClick = true
                loginPhoneButtonNextClick()
            }
        })
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

        // recyclerview click
        rvadapterLoginPhoneTelecom.itemClick = object : LoginPhoneTelecomAdapter.ItemClick {
            override fun onClick(view: View, position: Int, text: String) {
                binding.loginPhoneButtonTelecom.setText(text)
                binding.loginPhoneButtonTelecom.setTextColor(ContextCompat.getColor(this@LoginPhoneActivity, R.color.black))
                telecomBottomSheetDialog.dismiss()
                binding.loginPhoneLayoutPhoneNumber.visibility = View.VISIBLE
                binding.loginPhoneTitleTop.setText(R.string.login_phone_title_number_top)
                binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_number_bottom)
                showKeyboard(binding.loginPhoneEdittextPhoneNumber)
            }
        }
    }

    fun phoneNumberEdittext() {
        binding.loginPhoneEdittextPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.loginPhoneEdittextPhoneNumber.length() == 11) {
                    binding.loginPhoneLayoutPassword.visibility = View.VISIBLE
                    binding.loginPhoneTitleTop.setText(R.string.login_phone_title_password_top)
                    binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_password_bottom)
                    binding.loginPhoneEdittextPassword.requestFocus()
                }
            }
        })
    }

    fun passwordEdittext() {
        binding.loginPhoneEdittextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && !p0.toString().equals("")) {
                    binding.loginPhoneButtonNext.isEnabled = true
                    //termsButtonNext = true
                    binding.loginPhoneTitleTop.setText(R.string.login_phone_title_check_top)
                    binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_check_bottom)
                } else {
                    binding.loginPhoneButtonNext.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                loginPhoneButtonNextClick()
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun termsSelect() {
        // bottomsheetdialog&recyclerview 생성
        val termsBottomSheetView = layoutInflater.inflate(R.layout.dialog_login_phone_terms, null)
        val termsRecyclerView = termsBottomSheetView.findViewById<RecyclerView>(R.id.dialog_terms_recyclerview)
        itemTerms.add(LoginPhoneTermsItem("번개장터 이용약관 (필수)", false))
        itemTerms.add(LoginPhoneTermsItem("개인정보 수집 이용 동의 (필수)", false))
        itemTerms.add(LoginPhoneTermsItem("휴대폰 본인확인서비스 (필수)", false))
        itemTerms.add(LoginPhoneTermsItem("휴면시 개인정보 분리보관 동의 (필수)", false))
        itemTerms.add(LoginPhoneTermsItem("위치정보 이용약관 동의 (필수)", false))
        itemTerms.add(LoginPhoneTermsItem("개인정보 수집 이용 동의 (선택)", false))
        itemTerms.add(LoginPhoneTermsItem("마케팅 수신 동의 (선택)", false))
        itemTerms.add(LoginPhoneTermsItem("개인정보 광고활용 동의 (선택)", false))
        val rvadapterLoginPhoneTerms = LoginPhoneTermsAdapter(itemTerms)
        termsRecyclerView.adapter = rvadapterLoginPhoneTerms
        termsBottomSheetDialog = BottomSheetDialog(this)
        termsBottomSheetDialog.setContentView(termsBottomSheetView)

        loginPhoneButtonNextClick()

        //bottomsheetdialog click
        val termsAllAccept = termsBottomSheetView.findViewById<ConstraintLayout>(R.id.dialog_terms_accept_all)
        val termsAllAcceptImage = termsBottomSheetView.findViewById<ImageView>(R.id.dialog_terms_accept_all_imageview)
        val termsButtonCheck = termsBottomSheetView.findViewById<Button>(R.id.dialog_terms_button_confirm)
        var termsAllAcceptBoolean = false
        termsAllAccept.setOnClickListener {
            if (!termsAllAcceptBoolean) {
                termsAllAcceptBoolean = true
                termsAllAccept.setBackgroundResource(R.drawable.background_btn_terms_accept_all_checked)
                termsAllAcceptImage.setImageResource(R.drawable.icon_terms_accept_all_checked)
                termsButtonCheck.isEnabled = true
                Log.d("ccc", rvadapterLoginPhoneTerms.itemCount.toString())
                for (i in 0 until rvadapterLoginPhoneTerms.itemCount) {
                    rvadapterLoginPhoneTerms.itemList[i].termsCheckbox = true
                }
                rvadapterLoginPhoneTerms.notifyDataSetChanged()
            } else {
                termsAllAcceptBoolean = false
                termsAllAccept.setBackgroundResource(R.drawable.background_btn_terms_accept_all)
                termsAllAcceptImage.setImageResource(R.drawable.icon_terms_accept_all_unchecked)
                termsButtonCheck.isEnabled = false
                for (i in 0 until rvadapterLoginPhoneTerms.itemCount) {
                    rvadapterLoginPhoneTerms.itemList[i].termsCheckbox = false
                }
                rvadapterLoginPhoneTerms.notifyDataSetChanged()
            }
        }

        // recyclerview click
        rvadapterLoginPhoneTerms.itemClick = object : LoginPhoneTermsAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                if (rvadapterLoginPhoneTerms.itemList[0].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[1].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[2].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[3].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[4].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[5].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[6].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[7].termsCheckbox) {
                    termsAllAcceptBoolean = true
                    termsAllAccept.setBackgroundResource(R.drawable.background_btn_terms_accept_all_checked)
                    termsAllAcceptImage.setImageResource(R.drawable.icon_terms_accept_all_checked)
                } else if (rvadapterLoginPhoneTerms.itemList[0].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[1].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[2].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[3].termsCheckbox &&
                    rvadapterLoginPhoneTerms.itemList[4].termsCheckbox) {
                    termsButtonCheck.isEnabled = true
                } else {
                    termsAllAcceptBoolean = false
                    termsAllAccept.setBackgroundResource(R.drawable.background_btn_terms_accept_all)
                    termsAllAcceptImage.setImageResource(R.drawable.icon_terms_accept_all_unchecked)
                    termsButtonCheck.isEnabled = false
                }
            }
        }

        // 약관동의 버튼 click
        termsButtonCheck.setOnClickListener {
            binding.loginPhoneLayoutName.visibility = View.GONE
            binding.loginPhoneLayoutBirth.visibility = View.GONE
            binding.loginPhoneLayoutTelecom.visibility = View.GONE
            binding.loginPhoneLayoutPhoneNumber.visibility = View.GONE
            binding.loginPhoneLayoutPassword.visibility = View.GONE
            binding.loginPhoneLayoutShopName.visibility = View.VISIBLE

            termsBottomSheetDialog.dismiss()
            binding.loginPhoneTitleTop.setText(R.string.login_phone_title_shop_top)
            binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_shop_bottom)
            binding.loginPhoneButtonNext.isEnabled = false
            showKeyboard(binding.loginPhoneEdittextShopName)
        }
    }

    fun shopNameEdittext() {
        binding.loginPhoneEdittextShopName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.loginPhoneButtonNext.isEnabled = p0 != null && !p0.toString().equals("")
            }

            override fun afterTextChanged(p0: Editable?) {
                lastClick = true
                loginPhoneButtonNextClick()
            }
        })
    }

    fun showKeyboard(edittext: EditText) {
        edittext.requestFocus()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(edittext, 0)
    }

    fun loginPhoneButtonNextClick() {
        if (firstClick && !lastClick) {
            binding.loginPhoneButtonNext.setOnClickListener {
                binding.loginPhoneLayoutBirth.visibility = View.VISIBLE
                binding.loginPhoneEdittextBirth.requestFocus()
                binding.loginPhoneTitleTop.setText(R.string.login_phone_title_birth_top)
                binding.loginPhoneTitleBottom.setText(R.string.login_phone_title_birth_bottom)
                binding.loginPhoneButtonNext.setText(R.string.login_phone_button_next_check)
                binding.loginPhoneButtonNext.isEnabled = false
                firstClick = false
            }
        } else if (!firstClick && lastClick) {
            binding.loginPhoneButtonNext.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK) // loginactivity 종료
                startActivity(intent)
            }
        } else {
            binding.loginPhoneButtonNext.setOnClickListener {
                termsBottomSheetDialog.show()
            }
        }
    }

}