package com.example.leopa.testapplication.stepcounter
/*
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.leopa.testapplication.database.StepCounter

@Database(entities = [(StepCounter::class)], version = 1)
abstract class StepCounterDB: RoomDatabase() {
    abstract fun stepCounterDao(): StepCounterDao
    companion object {
        private var sInstance: StepCounterDB? = null
        @Synchronized
        fun get(context: Context): StepCounterDB {
            if (sInstance == null) {
                synchronized(StepCounterDB::class) {
                    sInstance = Room.databaseBuilder(context.applicationContext, StepCounterDB::class.java, "step.db").build()
                }
            }
            return sInstance!!
        }
    }
}*/