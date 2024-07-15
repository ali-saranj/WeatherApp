package com.kasra.weather.data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kasra.weather.data.local.model.CityInfoEntity
import com.kasra.weather.data.model.CityInfo

@Dao
interface CityInfoDao {
    @Insert
    suspend fun insert(cityInfoEntity: CityInfoEntity)

    @Query("SELECT * FROM city_info")
   suspend fun getAll(): List<CityInfoEntity>

    @Update
    suspend fun update(cityInfoEntity: CityInfoEntity)

}
