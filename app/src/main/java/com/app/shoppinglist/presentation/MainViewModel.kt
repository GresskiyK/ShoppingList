package com.app.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shoppinglist.data.ShopListRepositoryImpl
import com.app.shoppinglist.domain.*

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val getShopListItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)



    val shopList = getShopListCase.getShopList()


    fun deleteShopListItem(item:ShopItem){
        deleteShopItemUseCase.deleteShopItem(item)

    }


    fun getShopListItem(index: Int): ShopItem {
        return getShopListItemUseCase.getShopItem(index)
    }

    fun  changeShopItemState(item:ShopItem){
        val itemChanged = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(itemChanged)

    }
}