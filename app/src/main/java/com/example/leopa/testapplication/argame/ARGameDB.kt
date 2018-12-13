package com.example.leopa.testapplication.argame

/*
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.leopa.testapplication.database.ARGame

@Database(entities = [(ARGame::class)], version = 1)
abstract class ARGameDB: RoomDatabase() {
    abstract fun arGameDao(): ARGameDao
    companion object {
        private var sInstance: ARGameDB? = null
        @Synchronized
        fun get(context: Context): ARGameDB {
            if (sInstance == null) {
                synchronized(ARGameDB::class) {
                    sInstance = Room.databaseBuilder(context.applicationContext, ARGameDB::class.java, "game.db").build()
                }
            }
            return sInstance!!
        }
    }
}*/