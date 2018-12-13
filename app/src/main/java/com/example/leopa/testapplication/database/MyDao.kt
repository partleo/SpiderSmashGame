package com.example.leopa.testapplication.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface MyDao {

    @Query("SELECT * FROM argame WHERE id LIKE 1")
    fun getHighScore(): LiveData<ARGame>

    @Query("SELECT score FROM argame WHERE id LIKE :scoreId")
    fun comparePoints(scoreId: Int): Int

    @Query("SELECT id FROM argame WHERE id LIKE :scoreId")
    fun getSavedScoreId(scoreId: Int): Int

    @Query("SELECT * FROM argame WHERE id LIKE :scoreId")
    fun getSavedScore(scoreId: Int): LiveData<ARGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighScore(arGame: ARGame)

    @Query("SELECT * FROM stepcounter WHERE id LIKE 1")
    fun getSteps(): LiveData<StepCounter>

    @Query("SELECT steps FROM stepcounter WHERE id LIKE 1")
    fun getSavedSteps(): Int

    @Query("SELECT id FROM stepcounter WHERE id LIKE 1")
    fun getSavedStepsId(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSteps(stepCounter: StepCounter)

    @Query("SELECT * FROM shake WHERE id LIKE 1")
    fun getShake(): LiveData<Shake>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun shake(shake: Shake)
}