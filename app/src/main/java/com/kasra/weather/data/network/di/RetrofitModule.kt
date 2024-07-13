package com.kasra.weather.data.network.di


import com.kasra.weather.data.network.IWeatherApi
import com.kasra.weather.data.util.Content
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    /**
     * Provides a Retrofit instance configured with the base URL and Gson converter.
     *
     * @return A Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(Content.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    /**
     * Provides an instance of the IWeatherApi interface using the provided Retrofit instance.
     *
     * @param retrofit The Retrofit instance used to create the API.
     * @return An instance of IWeatherApi.
     */
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): IWeatherApi = retrofit.create(IWeatherApi::class.java)
}
