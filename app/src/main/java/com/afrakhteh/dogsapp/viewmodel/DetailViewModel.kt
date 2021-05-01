package com.afrakhteh.dogsapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.afrakhteh.dogsapp.model.database.DogsDatabase
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val detail = MutableLiveData<DogsModel>()

    fun fetch(uuid: Int) {
        getDetailFromDataBase(uuid)
    }

    private fun getDetailFromDataBase(uuid: Int) {
        launch {
            val dogsInfo = DogsDatabase(getApplication()).getDao().getOneItem(uuid)
            detail.value = dogsInfo
        }

    }
}