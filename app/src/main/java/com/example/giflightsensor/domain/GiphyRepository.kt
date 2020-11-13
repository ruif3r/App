package com.example.giflightsensor.domain

import com.example.giflightsensor.api.GiphyApi
import com.example.giflightsensor.api.NetworkResponse
import com.example.giflightsensor.api.data.GiphyGif
import java.lang.RuntimeException
import javax.inject.Inject

class GiphyRepository @Inject constructor (private val giphyApi: GiphyApi) {

    suspend fun getRandomGifs(): NetworkResponse<GiphyGif> = try {
        NetworkResponse.Success(giphyApi.getRandomGif())
    } catch (e: Exception) {
        NetworkResponse.Error(e)
    }
}