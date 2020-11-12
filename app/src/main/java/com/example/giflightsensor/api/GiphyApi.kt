package com.example.giflightsensor.api

import com.example.giflightsensor.api.data.GiphyGif
import retrofit2.http.GET

const val API_KEY = "OVzbdmogxLb6Zbv8rkSC1ua8cm6lksAB"

interface GiphyApi {

    @GET("random?api_key=$API_KEY&tag=&rating=g")
    suspend fun getRandomGif() : GiphyGif
}