package com.example.financei.repositories

import androidx.annotation.WorkerThread
import com.example.financei.database.daos.CountDao
import com.example.financei.database.models.Count
import kotlinx.coroutines.flow.Flow

class CountRepository(private val countDao: CountDao) {

    val allCounts : Flow<List<Count>> = countDao.getCounts()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(count: Count) {
        countDao.insert(count)
    }
}