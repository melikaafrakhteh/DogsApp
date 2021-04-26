package com.afrakhteh.dogsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrakhteh.dogsapp.model.DogsModel

class ListViewModel :ViewModel() {

    val dogs = MutableLiveData<List<DogsModel>>()
    val dogsLoadingError = MutableLiveData<Boolean>()
    val dogsLoading = MutableLiveData<Boolean>()

    fun refresh(){

    }
}