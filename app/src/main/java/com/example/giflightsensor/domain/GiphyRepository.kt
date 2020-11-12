package com.example.giflightsensor.domain

import com.example.giflightsensor.api.GiphyApi
import com.example.giflightsensor.api.NetworkResponse
import javax.inject.Inject

class GiphyRepository @Inject constructor (private val giphyApi: GiphyApi) {

    suspend fun getRandomGifs() = try {
        NetworkResponse.Success(giphyApi.getRandomGif())
    } catch (e: Exception) {
        NetworkResponse.Error(e)
    }
}