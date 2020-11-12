package com.example.giflightsensor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.giflightsensor.domain.GiphyRepository

class MainActivityViewModel (val repo : GiphyRepository) : ViewModel() {

    fun randomGif() = liveData { emit(repo.getRandomGifs()) }

}