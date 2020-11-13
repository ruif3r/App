package com.example.giflightsensor.domain

import com.example.giflightsensor.api.GiphyApi
import com.example.giflightsensor.api.NetworkResponse
import com.example.giflightsensor.api.data.Data
import com.example.giflightsensor.api.data.GiphyGif
import com.example.giflightsensor.api.data.Images
import com.example.giflightsensor.api.data.OriginalQualityGif
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert
import org.junit.Test

class GiphyRepositoryTest {

    @MockK
    private lateinit var giphyApi: GiphyApi

    private val giphyRepository: GiphyRepository

    private val defaultGiphyGif = GiphyGif(
        data = Data(
            images = Images(
                original = OriginalQualityGif("test gif url")
            )
        )
    )

    init {
        MockKAnnotations.init(this)
        giphyRepository = GiphyRepository(giphyApi)
    }

    @Test
    fun `given api response when getRandomGif then success with GiphyGif is returned`() {
        runBlocking {
//        given
            coEvery { giphyApi.getRandomGif() } returns defaultGiphyGif
//        when
            val result = giphyRepository.getRandomGifs()
//        then
            Assert.assertThat(result, instanceOf(NetworkResponse.Success::class.java))
            Assert.assertEquals(defaultGiphyGif, result.data)
            coVerify(exactly = 1) { giphyApi.getRandomGif() }

        }
    }

    @Test
    fun `given error api response when getRandomGif then Error is returned`() {
        runBlocking {
//        given
            coEvery { giphyApi.getRandomGif() } throws RuntimeException("test error")
//        when
            val result = giphyRepository.getRandomGifs()
//        then
            Assert.assertThat(result, instanceOf(NetworkResponse.Error::class.java))
            Assert.assertThat(result.error, instanceOf(RuntimeException::class.java))
            coVerify(exactly = 1) { giphyApi.getRandomGif() }

        }
    }
}