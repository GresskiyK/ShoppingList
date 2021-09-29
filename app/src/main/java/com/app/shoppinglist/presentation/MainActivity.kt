package com.app.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.app.shoppinglist.R
import com.app.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopItems:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llShopItems=findViewById(R.id.shopItemsLinear)

        var count = 0

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this){
            setLinear(it)
            Log.d("MainViewModelTest",it.toString())
        }

    }


    private fun setLinear(list:List<ShopItem>){

        var selectedView:Int

        for (item in list){
            if (item.enabled){
                selectedView = R.layout.shop_item_enabled
            }
            else{
                selectedView = R.layout.shop_item_disabled
            }

            val cell = LayoutInflater.from(this).inflate(selectedView,llShopItems,false)
            val tvName = cell.findViewById<TextView>(R.id.tv_name)
            val tvCount = cell.findViewById<TextView>(R.id.tv_count)
            tvName.text = item.name
            tvCount.text = item.count.toString()

            llShopItems.addView(cell)
        }


    }
}