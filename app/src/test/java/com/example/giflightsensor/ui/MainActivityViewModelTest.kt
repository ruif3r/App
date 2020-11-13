package com.example.giflightsensor.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.giflightsensor.api.NetworkResponse
import com.example.giflightsensor.api.data.Data
import com.example.giflightsensor.api.data.GiphyGif
import com.example.giflightsensor.api.data.Images
import com.example.giflightsensor.api.data.OriginalQualityGif
import com.example.giflightsensor.domain.GiphyRepository
import com.example.giflightsensor.ui.home.MainActivityViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.junit.*
import java.lang.Exception
import java.lang.RuntimeException

class MainActivityViewModelTest {

    @JvmField
    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var giphyRepository: GiphyRepository

    private val mainActivityViewModel: MainActivityViewModel

    private val defaultGiphyGif = GiphyGif(
        data = Data(
            images = Images(
                original = OriginalQualityGif("test gif url")
            )
        )
    )

    init {
        MockKAnnotations.init(this)
        mainActivityViewModel = MainActivityViewModel(giphyRepository)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun beforeTests() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        coEvery { giphyRepository.getRandomGifs() } returns NetworkResponse.Success(defaultGiphyGif)
    }

    @Test
    fun `given repository success response when getRandomGifs() then right value is emmited and loading false`() {
//        when
        mainActivityViewModel.getRandomGif()
        mainActivityViewModel.gifLiveData.observeForever { }
        mainActivityViewModel.isLoadingLiveData.observeForever { }
//        then
        val value = mainActivityViewModel.gifLiveData.value
        Assert.assertThat(
            value,
            CoreMatchers.instanceOf(GiphyGif::class.java)
        )
        Assert.assertEquals(defaultGiphyGif, value)
        Assert.assertEquals(false, mainActivityViewModel.isLoadingLiveData.value)
        coVerify(exactly = 1) { giphyRepository.getRandomGifs() }

    }

    @Test
    fun `given repository error response when getRandomGifs() then right value is emmited and is loading false`() {
//        given
        coEvery { giphyRepository.getRandomGifs() } returns NetworkResponse.Error(RuntimeException("test"))
//        when
        mainActivityViewModel.getRandomGif()
        mainActivityViewModel.gifErrorLiveData.observeForever { }
        mainActivityViewModel.isLoadingLiveData.observeForever { }
//        then
        val value = mainActivityViewModel.gifErrorLiveData.value
        Assert.assertThat(
            value,
            CoreMatchers.instanceOf(RuntimeException::class.java)
        )
        Assert.assertEquals(false, mainActivityViewModel.isLoadingLiveData.value)
        Assert.assertEquals("test", value?.message)
        coVerify(exactly = 1) { giphyRepository.getRandomGifs() }

    }

    @ExperimentalCoroutinesApi
    @After
    fun afterTests() {
        Dispatchers.resetMain()
    }

}