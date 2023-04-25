package com.example.thistothat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversions_table")
class Conversion (
    @PrimaryKey(autoGenerate = true)
    val ID: Int,
    val a: String,
    val b: String,
    val a2b: Float,
    val b2a: Float,
    val favorites: Boolean
    )