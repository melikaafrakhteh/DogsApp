package com.afrakhteh.dogsapp.model.api

import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.utils.Constant
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {

    private val api = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DogsApi::class.java)


    fun getDogs(): Single<List<DogsModel>> {
        return api.getDogs()
    }
}