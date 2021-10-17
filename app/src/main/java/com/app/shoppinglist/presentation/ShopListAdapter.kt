package com.app.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.shoppinglist.R
import com.app.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter:ListAdapter<ShopItem, ShopListAdapter.ShopListViewHolder>(ShopItemDiff()) {

//    var listOfItems = listOf<ShopItem>()
//        set(value) {
//            val callback = ShopListDiff(listOfItems,value)
//            val diffResult = DiffUtil.calculateDiff(callback)
//            diffResult.dispatchUpdatesTo(this)
//        field = value
//    }

    var onShopItemLongClickListener:((ShopItem)->Unit)? = null
    var onShopItemShortClickListener:((ShopItem)->Unit)? = null


    companion object{
        const val ENABLED_VIEW=1
        const val DISABLED_VIEW=0
        const val MAX_POOL_SIZE=15
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {

        val layout = when(viewType){
            ENABLED_VIEW->R.layout.shop_item_enabled
            DISABLED_VIEW->R.layout.shop_item_disabled
            else->throw RuntimeException("Unknown viewType")
        }
        val viewHolder = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ShopListViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = getItem(position)
            holder.tvName.text = item.name
            holder.tvCount.text = item.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(item)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemShortClickListener?.invoke(item)
        }
    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        // when view has gone from the screen and it setting up with default settings, before it will go to onBindViewHolder method again
        holder.tvName.text = "Empty"
        holder.tvCount.text = "default value"
    }

    override fun getItemViewType(position: Int): Int {

        val item = getItem(position)

        return if(item.enabled){
            ENABLED_VIEW
        }else{
            DISABLED_VIEW
        }
    }

    class ShopListViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }
}