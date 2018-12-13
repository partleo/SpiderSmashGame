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
import com.example.leopa.testapplication.MainActivity
import com.example.leopa.testapplication.database.DataBase
import com.example.leopa.testapplication.database.StepCounter
import org.jetbrains.anko.doAsync

class StepCounterService : Service(), SensorEventListener{

    private lateinit var mSensorManager: SensorManager
    private lateinit var stepDetectorSensor: Sensor
    private lateinit var db: DataBase
    private val mBinder = StepCounterBinder()
    private var stepCount: Int = 0
    private val currentStepsId: Int = 1
    private lateinit var mainActivity: MainActivity

    override fun onBind(intent: Intent): IBinder {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        mSensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI)
        return mBinder
    }

    override fun onCreate() {
        db = DataBase.get(this)
        getSavedSteps()
        mainActivity = MainActivity()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == stepDetectorSensor) {
            stepCount++
            saveSteps()
        }
    }

    inner class StepCounterBinder : Binder() {
        internal// Return this instance of LocalService so clients can call public methods
        val service: StepCounterService
            get() = this@StepCounterService
    }

    private fun saveSteps() {
        doAsync {
            db.myDao().insertSteps(StepCounter(currentStepsId, stepCount))
        }
    }

    private fun getSavedSteps() {
        doAsync {
            if (db.myDao().getSavedStepsId() == currentStepsId) {
                stepCount = db.myDao().getSavedSteps()
            }
        }
    }

    fun resetSteps() {
        doAsync {
            stepCount = 0
            db.myDao().insertSteps(StepCounter(currentStepsId, stepCount))

        }
    }
}