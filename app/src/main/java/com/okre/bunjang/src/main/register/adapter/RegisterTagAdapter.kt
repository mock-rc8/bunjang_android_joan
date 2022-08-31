package com.okre.bunjang.src.main.register.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemRegisterTagBinding

class RegisterTagAdapter(val itemList : MutableList<String>) : RecyclerView.Adapter<RegisterTagAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRegisterTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : String) {
            binding.itemTagTitle.text = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRegisterTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}