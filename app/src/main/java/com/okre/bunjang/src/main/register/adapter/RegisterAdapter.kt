package com.okre.bunjang.src.main.register.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okre.bunjang.databinding.ItemRegisterGalleryImageBinding

class RegisterAdapter(val itemList : MutableList<String>) : RecyclerView.Adapter<RegisterAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRegisterGalleryImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : String) {
            Glide.with(binding.itemRegisterGalleryImg.context)
                .load(item)
                .into(binding.itemRegisterGalleryImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRegisterGalleryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}