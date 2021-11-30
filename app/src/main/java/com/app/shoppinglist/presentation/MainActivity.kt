package com.app.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.shoppinglist.R
import com.app.shoppinglist.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setRecycler()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this){
            shopListAdapter.submitList(it)
            Log.d("MainViewModelTest",it.toString())
        }

        val buttonAdd = this.findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAdd.setOnClickListener{
            startActivity(ShopItemActivity.getIntentAddShopItem(this))
        }

    }

    private fun setRecycler(){
        val rvShopItems=findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopItems){
            shopListAdapter= ShopListAdapter()
            adapter=shopListAdapter
            rvShopItems.recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED_VIEW,ShopListAdapter.MAX_POOL_SIZE)
            rvShopItems.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_VIEW,ShopListAdapter.MAX_POOL_SIZE)
            shopItemLongClickListener()
            shopItemShortClickListener()
            setupDeleteAnimation(rvShopItems)
        }


    }
    private fun setupDeleteAnimation(recycler: RecyclerView){
        val callback = object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopListItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler)

    }

    private fun shopItemShortClickListener() {
        shopListAdapter.onShopItemShortClickListener = {
            startActivity(ShopItemActivity.getIntentEditShopItem(this,it.id))

            Log.d("ShopItem", it.count.toString() + " clicked")
        }
    }

    private fun shopItemLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeShopItemState(it)
        }
    }


}