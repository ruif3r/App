package com.example.giflightsensor.di

import com.example.giflightsensor.api.GiphyApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModuleTest {

    var mockWebServer: MockWebServer = MockWebServer()
    var giphyApi : GiphyApi? = null

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        giphyApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build().create(GiphyApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}