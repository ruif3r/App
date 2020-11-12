package com.example.giflightsensor

import android.app.Application
import com.example.giflightsensor.di.DaggerApplicationComponent

class App : Application() {

    val applicationComponent by lazy { DaggerApplicationComponent.factory().create(this) }
}