package com.okre.bunjang.src.main.home.buy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemBuyDeliveryAreaSelectBinding

class BuyDeliveryAreaSelectAdapter(val itemList : MutableList<BuyDeliveryAreaSelectItem>) : RecyclerView.Adapter<BuyDeliveryAreaSelectAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemBuyDeliveryAreaSelectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : BuyDeliveryAreaSelectItem) {
            binding.itemAreaSelectAddress.text = item.address
            binding.itemAreaSelectName.text = item.name
            binding.itemAreaSelectPhone.text = item.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ItemBuyDeliveryAreaSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}