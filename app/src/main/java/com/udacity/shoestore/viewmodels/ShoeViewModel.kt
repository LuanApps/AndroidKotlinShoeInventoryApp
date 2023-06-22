package com.udacity.shoestore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewModel: ViewModel() {

    private val _shoeList: MutableLiveData<MutableList<Shoe>> = MutableLiveData()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    fun addShoe(shoe: Shoe) {
        val currentList = _shoeList.value ?: mutableListOf()
        currentList.add(shoe)
        _shoeList.value = currentList
    }
}