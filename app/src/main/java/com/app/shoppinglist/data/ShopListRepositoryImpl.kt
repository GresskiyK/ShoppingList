package com.app.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.shoppinglist.domain.ShopItem
import com.app.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import java.util.*
import kotlin.random.Random

object ShopListRepositoryImpl:ShopListRepository{


    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopListItems = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id) })
    private var autoID = 0

    init {
        for (i in 0 until 100){
            val item = ShopItem("Name",i,Random.nextBoolean())
            addShopItem(item)
        }
    }


    private fun updateShopListLiveData(){
        shopListLiveData.postValue(shopListItems.toList())

    }
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoID++
        }
        shopListItems.add(shopItem)
        updateShopListLiveData()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListItems.remove(shopItem)
        updateShopListLiveData()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopListItems.remove(oldElement)
        addShopItem(shopItem)
        updateShopListLiveData()
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val requestedShopItem = shopListItems.find { it.id == shopItemId }
        return requestedShopItem ?:throw RuntimeException("Element not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }
}