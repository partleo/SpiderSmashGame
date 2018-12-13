package com.example.leopa.testapplication.stepcounter
/*
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.leopa.testapplication.database.StepCounter

@Dao
interface StepCounterDao {
    @Query("SELECT * FROM stepcounter")
    fun getAll(): LiveData<List<StepCounter>>

    @Query("SELECT * FROM stepcounter WHERE id LIKE 1")
    fun getSteps(): LiveData<StepCounter>

    @Query("SELECT steps FROM stepcounter WHERE id LIKE 1")
    fun getSavedSteps(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSteps(stepCounter: StepCounter)

}*/