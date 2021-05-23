package com.afrakhteh.dogsapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.utils.Constant


@Database(entities = [DogsModel::class], version = Constant.DATABASE_VERSION)
abstract class DogsDatabase : RoomDatabase() {
   abstract fun getDao(): DogDao

    companion object {
        @Volatile
        private var INSTANCE: DogsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDb(context).also {
                INSTANCE = it
            }
        }

        private fun buildDb(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                DogsDatabase::class.java,
                Constant.DATABASE_NAME)
                .build()
    }
}