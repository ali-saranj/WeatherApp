package com.kasra.weather.data.local.di

import android.content.Context
import androidx.room.Room
import com.kasra.weather.data.local.CityInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CityInfoDatabase::class.java, "CityInfo.db").build()

    @Provides
    @Singleton
    fun provideCityInfoDao(db: CityInfoDatabase) = db.cityInfoDao()

}