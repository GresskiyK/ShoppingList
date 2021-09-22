package com.app.shoppinglist.data

import com.app.shoppinglist.domain.ShopItem
import com.app.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl:ShopListRepository{


    private val shopListItems = mutableListOf<ShopItem>()
    private var autoID = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoID++
        }
        shopListItems.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListItems.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopListItems.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val requestedShopItem = shopListItems.find { it.id == shopItemId }
        return requestedShopItem ?:throw RuntimeException("Element not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopListItems.toList()
    }
}