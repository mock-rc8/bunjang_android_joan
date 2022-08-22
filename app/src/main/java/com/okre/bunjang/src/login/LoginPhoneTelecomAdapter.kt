package com.okre.bunjang.src.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemLoginPhoneTelecomBinding

class LoginPhoneTelecomAdapter(val itemList : MutableList<LoginPhoneTelecomItem>) : RecyclerView.Adapter<LoginPhoneTelecomAdapter.ViewHolder>() {

    var selectedPosition : Int = -1

    inner class ViewHolder(val binding: ItemLoginPhoneTelecomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : LoginPhoneTelecomItem) {
            binding.itemLoginPhoneTelecomTextview.text = item.telecomName
            binding.itemLoginPhoneTelecomLayout.setOnClickListener { v ->
                itemClick?.onClick(v, adapterPosition, item.telecomName.toString())

                //item.telecomCheckbox = true
                notifyDataSetChanged()
            }

            //binding.itemLoginPhoneTelecomCheckbox.isChecked = item.telecomCheckbox!!
        }
    }

    interface ItemClick {
        fun onClick(view : View, position: Int, text: String)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLoginPhoneTelecomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}