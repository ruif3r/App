package com.example.giflightsensor.di

import androidx.lifecycle.ViewModelProvider
import com.example.giflightsensor.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule{

    @Binds
    abstract fun bindViewModelFactory(factory : ViewModelFactory) : ViewModelProvider.Factory
}