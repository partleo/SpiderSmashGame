package com.example.leopa.testapplication.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ARGame(
        @PrimaryKey
        val id: Int,
        val score: Int
){
    override fun toString(): String = "$score"
}