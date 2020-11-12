package com.example.giflightsensor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.giflightsensor.api.NetworkResponse
import com.example.giflightsensor.ui.MainActivityViewModel
import com.example.giflightsensor.ui.lightsensor.LightSensorGraphActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    private val mainActivityViewModel by viewModels<MainActivityViewModel>{viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val randomGif = findViewById<ImageView>(R.id.main_random_gif_image_view)
        val randomButton = findViewById<Button>(R.id.main_random_button)

        subscribeToRandomGif(randomGif)
        randomButton.setOnClickListener { subscribeToRandomGif(randomGif) }
        randomGif.setOnClickListener {
            startActivity(Intent(this, LightSensorGraphActivity::class.java))
        }

    }

    private fun subscribeToRandomGif(iv: ImageView) {
        if (mainActivityViewModel.randomGif().hasObservers()){
            mainActivityViewModel.randomGif().removeObservers(this)
        }
            mainActivityViewModel.randomGif().observe(this, {
                when (it) {
                    is NetworkResponse.Success -> Glide.with(this).load(it.data?.data?.images?.original?.url).into(iv)
                    is NetworkResponse.Error -> Toast.makeText(this, it.error?.message, Toast.LENGTH_LONG).show()
                    is NetworkResponse.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
                }
            })
    }
}