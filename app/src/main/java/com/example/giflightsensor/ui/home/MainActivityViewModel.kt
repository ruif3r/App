package com.example.giflightsensor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giflightsensor.api.NetworkResponse
import com.example.giflightsensor.api.data.GiphyGif
import com.example.giflightsensor.domain.GiphyRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repo: GiphyRepository) : ViewModel() {

    private val gif = MutableLiveData<GiphyGif>()
    private val error = MutableLiveData<Exception>()
    private val isLoading = MutableLiveData<Boolean>()

    val gifLiveData: LiveData<GiphyGif>
        get() = gif

    val gifErrorLiveData: LiveData<Exception>
        get() = error

    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoading


    fun getRandomGif() {
        isLoading.value = true
        viewModelScope.launch {
            when (val gifResult = repo.getRandomGifs()) {
                is NetworkResponse.Success -> onSuccess(gifResult)
                is NetworkResponse.Error -> onError(gifResult)
            }
        }
    }

    private fun onError(gifResult: NetworkResponse<GiphyGif>) {
        error.value = gifResult.error
        isLoading.value = false
    }

    private fun onSuccess(gifResult: NetworkResponse<GiphyGif>) {
        gif.value = gifResult.data
        isLoading.value = false
    }


}