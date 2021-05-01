package com.afrakhteh.dogsapp.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.afrakhteh.dogsapp.model.datamodel.DogsModel

@Dao
interface DogDao {

    @Insert()
    suspend fun insert(vararg dogsModel: DogsModel): List<Long>

    @Delete
    suspend fun delete(vararg dogsModel: DogsModel)

    @Query(" DELETE FROM DogsModel")
    suspend fun deleteAllData()

    @Query(" SELECT * FROM DogsModel ")
    suspend fun getAllData(): List<DogsModel>

    @Query(" SELECT * FROM DogsModel WHERE uuid = :id")
    suspend fun getOneItem(id: Int): DogsModel
}