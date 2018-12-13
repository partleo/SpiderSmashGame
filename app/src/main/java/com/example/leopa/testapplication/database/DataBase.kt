package com.example.leopa.testapplication.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(StepCounter::class), (ARGame::class), (Shake::class)], version = 1)
//@TypeConverters(Converter::class)

abstract class DataBase: RoomDatabase() {
    abstract fun myDao(): MyDao
    companion object {
        private var sInstance: DataBase? = null
        @Synchronized
        fun get(context: Context): DataBase {
            if (sInstance == null) {
                synchronized(DataBase::class) {
                    sInstance = Room.databaseBuilder(context.applicationContext, DataBase::class.java, "mynewtestdatabase.db").build()
                }
            }
            return sInstance!!
        }
    }
}