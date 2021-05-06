package com.afrakhteh.dogsapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.afrakhteh.dogsapp.model.api.DogsApiService
import com.afrakhteh.dogsapp.model.database.DogsDatabase
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.utils.Notification
import com.afrakhteh.dogsapp.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private var sharedResult = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val dogsApiService = DogsApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogsModel>>()
    val dogsLoadingError = MutableLiveData<Boolean>()
    val dogsLoading = MutableLiveData<Boolean>()

    fun refresh() {
        val updateTime = sharedResult.getUpdateTime()
        val isLessThanTime = updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime
        if (isLessThanTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }

    }

    private fun fetchFromDatabase() {
        dogsLoading.value = true
        launch {
            val dogs = DogsDatabase(getApplication()).getDao().getAllData()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "fetch from local database", Toast.LENGTH_LONG).show()

        }

    }

    private fun fetchFromRemote() {
        dogsLoading.value = true
        disposable.add(
                dogsApiService.getDogs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<DogsModel>>() {
                            override fun onSuccess(t: List<DogsModel>) {
                                storeLocallyData(t)
                                Notification(getApplication()).createNotification()
                            }

                            override fun onError(e: Throwable) {
                                dogsLoadingError.value = true
                                dogsLoading.value = false
                                e.printStackTrace()
                                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show()
                            }
                        })
        )
    }

    private fun dogsRetrieved(t: List<DogsModel>) {
        dogs.value = t
        dogsLoading.value = false
        dogsLoadingError.value = false
    }

    private fun storeLocallyData(t: List<DogsModel>) {
        launch {
            val db = DogsDatabase(getApplication()).getDao()
            db.deleteAllData()
            val result = db.insert(*t.toTypedArray())
            var i = 0
            while (i < t.size) {
                t[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(t)
        }
        sharedResult.timeSaver(System.nanoTime())
    }

    fun refreshByPassCache() {
        fetchFromRemote()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}