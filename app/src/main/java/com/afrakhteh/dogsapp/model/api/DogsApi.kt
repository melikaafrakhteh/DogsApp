package com.afrakhteh.dogsapp.model.api

import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.utils.Constant
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {

    @GET(Constant.END_POINT_URL)
    fun getDogs(): Single<List<DogsModel>>
}