package com.afrakhteh.dogsapp.model

import com.google.gson.annotations.SerializedName

data class DogsModel(

        @SerializedName("id")
        val dogId: String?,

        @SerializedName("name")
        val dogBread: String?,

        @SerializedName("life_span")
        val dogLifeSpan: String?,

        @SerializedName("breed_group")
        val dogBreadGroup: String?,

        @SerializedName("bred_for")
        val dogBreadFor: String?,

        @SerializedName("temperament")
        val temperament: String?,

        @SerializedName("url")
        val imageUrl: String?
)
