package com.afrakhteh.dogsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrakhteh.dogsapp.model.DogsModel

class DetailViewModel : ViewModel() {

    val detail = MutableLiveData<DogsModel>()

    fun fetch(){

    }
}