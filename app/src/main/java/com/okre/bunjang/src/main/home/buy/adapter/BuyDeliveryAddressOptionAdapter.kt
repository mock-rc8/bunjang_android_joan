package com.okre.bunjang.src.main.home.buy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemBuyDeliveryAddressOptionBinding

class BuyDeliveryAddressOptionAdapter(val itemList : MutableList<String>) : RecyclerView.Adapter<BuyDeliveryAddressOptionAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemBuyDeliveryAddressOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : String) {
            binding.itemAddressOptionsTitle.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ItemBuyDeliveryAddressOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}