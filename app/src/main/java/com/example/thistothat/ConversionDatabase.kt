package com.example.thistothat

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Conversion::class],
    version = 1
)
abstract class ConversionDatabase: RoomDatabase() {

    abstract val dao: ConversionDao
}