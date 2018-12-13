package com.example.leopa.testapplication.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Shake(
        @PrimaryKey
        val id: Int,
        val shake: Int
){
    override fun toString(): String = "$shake"
}