package com.okre.bunjang.src.login.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemLoginPhoneTermsBinding
import com.okre.bunjang.src.login.item.LoginPhoneTermsItem

class LoginPhoneTermsAdapter(val itemList : MutableList<LoginPhoneTermsItem>) : RecyclerView.Adapter<LoginPhoneTermsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemLoginPhoneTermsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : LoginPhoneTermsItem) {
            binding.itemTermsTextview.text = item.termsName
            binding.itemTermsCheck.isChecked = item.termsCheckbox
            binding.itemTermsLayout.setOnClickListener { v ->
                item.termsCheckbox = item.termsCheckbox == false
                notifyDataSetChanged()
                itemClick?.onClick(v, adapterPosition)
            }
        }
    }

    interface ItemClick {
        fun onClick(view : View, position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLoginPhoneTermsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}