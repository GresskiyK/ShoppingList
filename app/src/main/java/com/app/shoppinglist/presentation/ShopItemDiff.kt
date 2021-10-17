package com.app.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.app.shoppinglist.domain.ShopItem

class ShopItemDiff:DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}