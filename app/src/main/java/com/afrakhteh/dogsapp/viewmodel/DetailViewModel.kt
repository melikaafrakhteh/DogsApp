package com.afrakhteh.dogsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrakhteh.dogsapp.model.datamodel.DogsModel

class DetailViewModel : ViewModel() {

    val detail = MutableLiveData<DogsModel>()

    fun fetch(){

    }
}