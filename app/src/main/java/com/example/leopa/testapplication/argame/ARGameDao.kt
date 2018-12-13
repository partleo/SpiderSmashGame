package com.example.leopa.testapplication.argame
/*
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.leopa.testapplication.database.ARGame

@Dao
interface ARGameDao {
    @Query("SELECT * FROM argame")
    fun getAll(): LiveData<List<ARGame>>

    @Query("SELECT * FROM argame WHERE id LIKE 1")
    fun getHighScore(): LiveData<ARGame>

    @Query("SELECT * FROM argame WHERE id LIKE :id")
    fun findById(id: Int): ARGame

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(arGame: ARGame)

    @Update
    fun update(arGame: ARGame)
}*/