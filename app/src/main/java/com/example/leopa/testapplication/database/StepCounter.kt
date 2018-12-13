package com.example.leopa.testapplication.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class StepCounter(
        @PrimaryKey
        val id: Int,
        val steps: Int
){
    override fun toString(): String = "$steps"
}