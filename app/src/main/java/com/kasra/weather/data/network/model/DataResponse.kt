package com.kasra.weather.data.network.model


import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<CityDto>
)