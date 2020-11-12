package com.example.giflightsensor.ui.lightsensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giflightsensor.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class LightSensorGraphActivity : AppCompatActivity(), SensorEventListener {

    private var lightSensorValues : TextView? = null
    private lateinit var sensorManager : SensorManager
    private var lightSensor : Sensor? = null
    private var lineChart : LineChart? = null
    private val entries = ArrayList<Entry>()
    private var isLightSensorActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor_graph)
        lightSensorValues = findViewById(R.id.light_sensor_values_text_view)
        lineChart = findViewById(R.id.lineChart)
        val freezeButton = findViewById<Button>(R.id.light_sensor_activity_freeze_button)
        getDeviceLightSensor()
        freezeButton.setOnClickListener {
            isLightSensorActive = if (isLightSensorActive) {
                sensorManager.unregisterListener(this)
                false
            } else {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
                true
            }
        }
    }

    private fun getDeviceLightSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        } else Toast.makeText(
            this,
            getString(R.string.light_sensor_not_available),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val currentValue = event?.values?.get(0) ?: 0
        lightSensorValues?.text = event?.values?.get(0).toString()
        val currentValueTimeStamp = event?.timestamp ?: 0
        val timeDifference =- currentValueTimeStamp ?: 0
        setValuesToChart(currentValueTimeStamp, timeDifference, currentValue)

    }

    private fun setValuesToChart(
        currentValueTimeStamp: Long,
        timeDifference: Long,
        currentValue: Number
    ) {
        entries.add(
            Entry(
                currentValueTimeStamp.minus((timeDifference.toFloat())).div(1000),
                currentValue.toFloat()
            )
        )
        val lineDataSet = LineDataSet(entries, "Illuminance")
        val iLineDataSet = ArrayList<ILineDataSet>().apply { add(lineDataSet) }
        lineChart?.data = LineData(iLineDataSet)
        lineChart?.invalidate()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        isLightSensorActive = true
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        isLightSensorActive = false
        sensorManager.unregisterListener(this)
    }
}