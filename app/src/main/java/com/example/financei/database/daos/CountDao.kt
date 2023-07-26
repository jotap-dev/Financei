package com.example.financei.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.financei.database.models.Count
import kotlinx.coroutines.flow.Flow

@Dao
interface CountDao {

    @Query("SELECT * FROM gastos")
    fun getCounts() : Flow<List<Count>>

    @Insert
    suspend fun insert(count: Count)

    @Query("DELETE FROM gastos")
    suspend fun deleteAll()

}