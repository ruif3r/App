package com.example.giflightsensor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.giflightsensor.domain.GiphyRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor (private val repo : GiphyRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GiphyRepository::class.java).newInstance(repo)
    }
}