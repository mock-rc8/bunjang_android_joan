package com.okre.bunjang.src.main.register.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemRegisterCategoryBinding
import com.okre.bunjang.src.main.register.item.CategoryResult

class RegisterCategoryAdapter(val itemList : MutableList<CategoryResult>) : RecyclerView.Adapter<RegisterCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRegisterCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : CategoryResult) {
            binding.categoryTitle.text = item.name
            if (!item.mainBoolean) {
                binding.categoryNext.visibility = View.INVISIBLE
            }
            binding.categoryLayout.setOnClickListener { v ->
                Log.d("dddd", "click")
                itemClick?.onClick(v, adapterPosition, item.name, item.Idx)

            }
        }
    }

    interface ItemClick {
        fun onClick(view : View, position: Int, name: String, Idx: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRegisterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}