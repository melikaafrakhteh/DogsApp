package com.afrakhteh.dogsapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.afrakhteh.dogsapp.model.datamodel.DogsModel

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dogsModel: DogsModel): List<Long>

    @Delete
    suspend fun delete(vararg dogsModel: DogsModel)

    @Query(" DELETE FROM DogsModel")
    suspend fun deleteAllData()

    @Query(" SELECT * FROM DogsModel ")
    fun getAllData(): LiveData<List<DogsModel>>

    @Query(" SELECT * FROM DogsModel WHERE uuid = :id")
    fun getOneItem(id: Int): DogsModel
}