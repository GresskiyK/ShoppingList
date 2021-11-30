package com.app.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shoppinglist.data.ShopListRepositoryImpl
import com.app.shoppinglist.domain.*
import java.lang.Exception

class ShopItemViewModel:ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopListItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() {
            return _errorInputName
        }

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount:LiveData<Boolean>
        get() {
            return _errorInputCount
        }

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem:LiveData<ShopItem>
        get() {
            return _shopItem
        }


    private val _taskFinished = MutableLiveData<Unit>()
    val taskFinished: LiveData<Unit>
        get() = _taskFinished

    fun getShopItem(id:Int){
        val item = getShopListItemUseCase.getShopItem(id)
        _shopItem.value = item
    }

    fun editShopItem(inputName:String?,inputCount:String?){
        val inputName = parseNameField(inputName)
        val inputCount = parseCountField(inputCount)

        if(validateInput(inputName,inputCount)){
            _shopItem.value?.let {
                val item = it.copy(name = inputName,count = inputCount)
                editShopItemUseCase.editShopItem(item)
                tasksFinished()
            }
        }

    }

    fun addShopItem(inputName:String?,inputCount:String?){
        val name = parseNameField(inputName)
        val count = parseCountField(inputCount)

        if(validateInput(name,count)){
            val shopItem = ShopItem(name,count,true)
            addShopItemUseCase.addShopItem(shopItem)
            tasksFinished()

        }

    }

    private fun parseNameField(name:String?):String{
        return name?.trim() ?: "Incorrect Name"
    }

    private fun parseCountField(count:String?):Int{
        return try{
            count?.trim()?.toInt() ?: 0
        }catch (e:Exception){
            0
        }
    }

    private fun validateInput(name:String,count:Int):Boolean{
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    fun tasksFinished(){
        _taskFinished.value = Unit
    }

}