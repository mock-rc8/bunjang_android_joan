package com.okre.bunjang.src.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.okre.bunjang.databinding.DialogLoginPhoneTermsBinding

class LoginPhoneTermsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogLoginPhoneTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLoginPhoneTermsBinding.inflate(inflater, container, false)
        return binding.root
    }
}