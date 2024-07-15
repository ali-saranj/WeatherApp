package com.kasra.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kasra.weather.data.local.Dao.CityInfoDao
import com.kasra.weather.data.local.model.CityInfoEntity

@Database(entities = [CityInfoEntity::class], version = 1)
abstract class CityInfoDatabase : RoomDatabase() {
    abstract fun cityInfoDao(): CityInfoDao
}