package com.afrakhteh.dogsapp.model.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DogsModel(

        @ColumnInfo(name = "dog_id")
        @SerializedName("id")
        val dogId: String?,

        @ColumnInfo(name = "dog_name")
        @SerializedName("name")
        val dogBread: String?,

        @ColumnInfo(name = "dog_lifespan")
        @SerializedName("life_span")
        val dogLifeSpan: String?,

        @ColumnInfo(name = "dog_group")
        @SerializedName("breed_group")
        val dogBreadGroup: String?,

        @ColumnInfo(name = "dog_for")
        @SerializedName("bred_for")
        val dogBreadFor: String?,

        @ColumnInfo(name = "dog_temp")
        @SerializedName("temperament")
        val temperament: String?,

        @ColumnInfo(name = "dog_image")
        @SerializedName("url")
        val imageUrl: String?
){
        @PrimaryKey(autoGenerate = true)
        var uuid :Int = 0
}
