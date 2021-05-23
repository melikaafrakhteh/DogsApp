package com.afrakhteh.dogsapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.afrakhteh.dogsapp.model.database.DogsDatabase
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import kotlinx.coroutines.launch


class SearchViewModel(application: Application) : BaseViewModel(application) {

    val dogs = MutableLiveData<List<DogsModel>>()
    val searchLoading = MutableLiveData<Boolean>()

    fun searchDataInDB() {
        searchLoading.value = true
        launch {
            val dogs = DogsDatabase(getApplication()).getDao().getAllData()
            dogsRetrieved(dogs)
        }

    }

    private fun dogsRetrieved(t: List<DogsModel>) {
        dogs.value = t
        searchLoading.value = false
    }

}