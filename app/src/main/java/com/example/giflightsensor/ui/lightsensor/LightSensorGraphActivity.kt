package com.example.giflightsensor.ui.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giflightsensor.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry

class LightSensorGraphActivity : AppCompatActivity(), SensorEventListener {

    private var lightSensorValues : TextView? = null
    private lateinit var sensorManager : SensorManager
    private var lightSensor : Sensor? = null
    private var lineChart : LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor_graph)
        lightSensorValues = findViewById(R.id.light_sensor_values_text_view)
        lineChart = findViewById(R.id.lineChart)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        }else Toast.makeText(this, getString(R.string.light_sensor_not_available), Toast.LENGTH_LONG).show()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val currentValue = event?.values?.get(0) ?: 0
        lightSensorValues?.text = event?.values?.get(0).toString()
        val timeDifference = event?.timestamp ?: 0
        val currentValueTimeStamp = event?.timestamp ?: 0
        val entries = ArrayList<Entry>()
        entries.add(Entry(currentValueTimeStamp.minus((timeDifference.toFloat())),
            currentValue as Float
        ))

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}