package com.okre.bunjang.src.main.home.buy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemBuyDeliveryAddressManageBinding
import com.okre.bunjang.databinding.ItemBuyDeliveryAreaSelectBinding
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAddressManageItem
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem

class BuyDeliveryAddressManageAdapter(val itemList : MutableList<BuyDeliveryAddressManageItem>) : RecyclerView.Adapter<BuyDeliveryAddressManageAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemBuyDeliveryAddressManageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : BuyDeliveryAddressManageItem) {
            binding.itemAddressManagerName.text = item.name
            binding.itemAddressManagerAddress.text = item.address
            binding.itemAddressManagerPhone.text = item.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ItemBuyDeliveryAddressManageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}