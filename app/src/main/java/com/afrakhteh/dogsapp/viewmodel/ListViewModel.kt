package com.afrakhteh.dogsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrakhteh.dogsapp.model.api.DogsApiService
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val dogsApiService = DogsApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogsModel>>()
    val dogsLoadingError = MutableLiveData<Boolean>()
    val dogsLoading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        dogsLoading.value = true
        disposable.add(
                dogsApiService.getDogs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<DogsModel>>() {
                            override fun onSuccess(t: List<DogsModel>) {
                                dogs.value = t
                                dogsLoading.value = false
                                dogsLoadingError.value = false
                            }

                            override fun onError(e: Throwable) {
                                dogsLoadingError.value = true
                                dogsLoading.value = false
                                e.printStackTrace()
                            }
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}