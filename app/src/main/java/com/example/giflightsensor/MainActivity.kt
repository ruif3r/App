package com.example.giflightsensor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.giflightsensor.ui.home.MainActivityViewModel
import com.example.giflightsensor.ui.lightsensor.LightSensorGraphActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainActivityViewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    private lateinit var randomGif: ImageView
    private lateinit var randomButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        subscribe()

        mainActivityViewModel.getRandomGif()
    }

    private fun initView() {
        randomGif = findViewById(R.id.main_random_gif_image_view)
        randomButton = findViewById(R.id.main_random_button)

        randomButton.setOnClickListener { mainActivityViewModel.getRandomGif() }
        randomGif.setOnClickListener {
            startActivity(Intent(this, LightSensorGraphActivity::class.java))
        }
    }

    private fun subscribe() {
        mainActivityViewModel.gifErrorLiveData.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }

        mainActivityViewModel.isLoadingLiveData.observe(this) {
            if (it) {
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
            }
        }

        mainActivityViewModel.gifLiveData.observe(this) {
            Glide.with(this).load(it.data.images.original.url).centerCrop().into(randomGif)
        }
    }

}