package com.example.leopa.testapplication.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import com.example.leopa.testapplication.ARActivity
import com.example.leopa.testapplication.database.DataBase
import com.example.leopa.testapplication.database.Shake
import org.jetbrains.anko.doAsync

class ShakeService : Service(), SensorEventListener {

    private lateinit var db: DataBase
    private lateinit var mSensorManager: SensorManager
    private lateinit var shakeDetectorSensor: Sensor
    private val mBinder = ShakeBinder()
    private lateinit var arActivity: ARActivity
    private var mAccel: Float = 0.toFloat() // acceleration apart from gravity
    private var mAccelCurrent: Float = 0.toFloat() // current acceleration including gravity
    private var mAccelLast: Float = 0.toFloat() // last acceleration including gravity
    private val shakeId = 1
    private val shake = 1


    override fun onBind(intent: Intent): IBinder {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager.registerListener(this, shakeDetectorSensor, SensorManager.SENSOR_DELAY_UI)

        return mBinder
    }

    override fun onCreate() {
        db = DataBase.get(this)
        arActivity = ARActivity()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        mAccelLast = mAccelCurrent
        mAccelCurrent = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val delta = mAccelCurrent - mAccelLast
        mAccel = mAccel * 0.9f + delta // perform low-cut filter

        if (mAccel > 20) {
            shakeDetected()
        }
    }

    inner class ShakeBinder : Binder() {
        internal// Return this instance of LocalService so clients can call public methods
        val service: ShakeService
            get() = this@ShakeService
    }

    fun shakeDetected() {
        doAsync {
            db.myDao().shake(Shake(shakeId, shake))
        }
    }

}