package com.example.thistothat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionDao {

    @Insert
    suspend fun insertConversion(conversion: Conversion)

    @Delete
    suspend fun deleteConversion(conversion: Conversion)

    @Query("SELECT * FROM conversions_table ORDER BY ID ASC")
    fun getConversionsOrderedById(): Flow<List<Conversion>>
}